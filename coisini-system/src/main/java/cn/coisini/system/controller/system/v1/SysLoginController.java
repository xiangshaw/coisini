package cn.coisini.system.controller.system.v1;

import cn.coisini.common.exception.CoisiniException;
import cn.coisini.common.utils.JwtUtil;
import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.pojo.SysUser;
import cn.coisini.model.system.vo.LoginVo;
import cn.coisini.system.service.SysUserService;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaoxiang
 * @Description: 用户登录 前端控制器
 */
@Api(tags = "用户登录")
@RestController
@RequestMapping("/api/v1/index")
@Log4j2
public class SysLoginController {
    private final SysUserService sysUserService;
    private final StringRedisTemplate stringRedisTemplate;
    private final Producer producer;

    public SysLoginController(SysUserService sysUserService, StringRedisTemplate stringRedisTemplate, Producer producer) {
        this.sysUserService = sysUserService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.producer = producer;
    }


    @ApiOperation("获取用户信息以及权限")
    @GetMapping("/info")
    public Result<Map<String,Object>> info(HttpServletRequest request){
        //获取请求头里面的token字符串
        String token = request.getHeader("token");
        Long userId = JwtUtil.getUserId(token);
        String username = JwtUtil.getUsername(token);
        Map<String,Object> map = sysUserService.getUserInfo(userId,username);
        return Result.ok(map);
    }

    @ApiOperation("获取验证码")
    @GetMapping("/code")
    public Result<Map<String, Object>> code() throws IOException {
        //生成唯一识别
        String uuid = IdUtil.simpleUUID();
        String  text = producer.createText();
        //验证码存入redis中，有效期为3分钟
        stringRedisTemplate.opsForValue().set("checkCode:" + uuid,text,3, TimeUnit.MINUTES);
        BufferedImage image = producer.createImage(text);
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(image,"jpg",fos);
        String codeImage = Base64.encodeBase64String(fos.toByteArray());
        Map<String, Object> map = new HashMap<>();
        map.put("code",codeImage);
        map.put("uuid",uuid);
        return Result.ok(map);
    }
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result<ResultEnum> logout(){
        return Result.ok(ResultEnum.SUCCESS);
    }

    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<SysUser> registerUser(@RequestBody SysUser sysUser) {
        return sysUserService.registerUser(sysUser);
    }

    // 登录交给Spring Security管理
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginVo loginVo) {
        String checkCode = stringRedisTemplate.opsForValue().get("checkCode:" + loginVo.getUuid());
        if (CharSequenceUtil.isBlank(checkCode)) {
            return Result.error(ResultEnum.SECURITY_CODE);
        }
        if (!checkCode.equals(loginVo.getCode())) {
            return Result.error(ResultEnum.SECURITY_CODE_ERROR);
        }
        // 根据username查询数据
        SysUser sysUser = sysUserService.getUserInfoByName(loginVo.getUsername());
        // 如果查询为空
        if (sysUser == null) {
            throw new CoisiniException(ResultEnum.PARAM_INVALID);
        }
        // 判断密码是否一致（加密后进行数据库密码比较）
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.encode(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new CoisiniException(ResultEnum.LOGIN_PASSWORD_ERROR);
        }
        // 判断用户是否可用（0正常 1禁用）
        if (Boolean.FALSE.equals(sysUser.getStatus())) {
            throw new CoisiniException(ResultEnum.ACCOUNT_STOP);
        }
        // 根据userId和username生成token字符串，通过map返回
        Map<String, Object> map = new HashMap<>();
        String token = JwtUtil.getToken(sysUser.getId(), sysUser.getUsername());
        map.put("token", token);
        log.info("map中的token：" + token);
        return Result.ok(map);
    }
}
