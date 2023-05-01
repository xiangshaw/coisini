package cn.coisini.system.security;

import cn.coisini.model.system.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

/**
 * @Author: xiaoxiang
 * @Description:
 */
public class CustomUser extends User {
    /**
     * 我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象
     */
    private final SysUser sysUser;

    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CustomUser that = (CustomUser) o;

        return Objects.equals(sysUser, that.sysUser);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sysUser != null ? sysUser.hashCode() : 0);
        return result;
    }
}
