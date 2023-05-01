package cn.coisini.system.security;


import cn.coisini.common.exception.CoisiniException;
import cn.coisini.model.system.pojo.SysUser;
import cn.coisini.system.service.SysMenuService;
import cn.coisini.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiaoxiang
 * @Description: 三、添加UserDetailsServiceImpl类，实现UserDetailsService接口
 */
// 交给spring进行管理
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private final SysUserService userService;
    private final SysMenuService menuService;

    public MyUserDetailsServiceImpl(SysUserService userService, SysMenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getOne(new QueryWrapper<SysUser>().eq("username", username));
        if (sysUser == null){
            throw new CoisiniException(500,"用户不存在");
        }
        if (Boolean.TRUE.equals(sysUser.getStatus())){
            throw new CoisiniException(500,"用户被禁用");
        }
        // 根据userid查询操作权限数据
        List<String> userPermsList = menuService.findUserButtonList(sysUser.getId());
        List<SimpleGrantedAuthority> grantedAuthorityList = userPermsList.stream().map(perms -> new SimpleGrantedAuthority(perms.trim())).collect(Collectors.toList());
        return new CustomUser(sysUser, grantedAuthorityList);
    }
}
