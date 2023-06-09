package cn.coisini.system.security;

import cn.coisini.common.exception.CoisiniException;
import cn.coisini.common.utils.IpUtils;
import cn.coisini.common.utils.JwtUtil;
import cn.coisini.log.service.AsyncLoginLogService;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.vo.LoginVo;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaoxiang
 * @Description: 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 */
@Configuration
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final StringRedisTemplate stringRedisTemplate;
    private final AsyncLoginLogService asyncLoginLogService;

    // 构造
    public TokenLoginFilter(
            AuthenticationManager authenticationManager,
            StringRedisTemplate stringRedisTemplate,
            AsyncLoginLogService asyncLoginLogService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/index/login", "POST"));
        this.stringRedisTemplate = stringRedisTemplate;
        this.asyncLoginLogService = asyncLoginLogService;
    }

    /**
     * 登录认证 获取用户名和密码
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);
            String checkCode = stringRedisTemplate.opsForValue().get("checkCode:" + loginVo.getUuid());
            // 验证码校验
            if (CharSequenceUtil.isBlank(checkCode)) {
                // 优雅返回错误给前端
                out(res,Result.error(ResultEnum.SECURITY_CODE));
                // 错误得抛
                throw new CoisiniException(500,"验证码不存在或已过期");
            }
            if (!checkCode.equals(loginVo.getCode())) {
                out(res,Result.error(ResultEnum.SECURITY_CODE_ERROR));
                throw new CoisiniException(500,"验证码错误");
            }
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            out(res,Result.error(ResultEnum.SERVER_ERROR));
            throw new CoisiniException(ResultEnum.SERVICE_ERROR);
        }
    }

    /**
     * 登录成功  认证成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // 获取认证对象
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        //保存权限数据
        stringRedisTemplate.opsForValue().set(customUser.getUsername(),
                JSON.toJSONString(customUser.getAuthorities()));

        // 生成token
        String token = JwtUtil.getToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        String username = JwtUtil.getUsername(token);
        Long userId = JwtUtil.getUserId(token);
        String ip = IpUtils.getIp(request);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        out(response, Result.ok(map));
        // 记录登录日志
        try {
            asyncLoginLogService.recordLoginLog(userId, username, false, ip, IpUtils.getIp2region(ip), IpUtils.getCityInfo(ip), "登录成功");
        } catch (Exception e) {
            logger.error("记录登录日志出错！", e);
        }
    }

    /**
     * 登录失败 认证失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {

        if (e.getCause() instanceof RuntimeException) {
            out(response, Result.error(ResultEnum.FAIL, e.getMessage()));
        } else {
            out(response, Result.error(ResultEnum.LOGIN_PASSWORD_ERROR));
        }
    }

    /**
     * @Description: 返回登录认证数据工具类
     */
    public static void out(HttpServletResponse response, Result<ResultEnum> r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
