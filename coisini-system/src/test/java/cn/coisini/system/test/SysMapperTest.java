package cn.coisini.system.test;

import cn.coisini.common.utils.IpUtils;
import cn.coisini.model.system.pojo.SysDept;
import cn.coisini.model.system.pojo.SysRole;
import cn.coisini.model.system.pojo.SysUser;
import cn.coisini.system.mapper.SysDeptMapper;
import cn.coisini.system.mapper.SysRoleMapper;
import cn.coisini.system.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@SpringBootTest
class SysMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    // 查询所有角色
    @Test
    void FindAll(){
        List<SysRole> roleList = sysRoleMapper.selectList(null);
        for (SysRole sysRole : roleList) {
            System.out.println(sysRole);
        }
    }

    // 查询所以用户
    @Test
    void FindAllUser(){
        List<SysUser> userList = sysUserMapper.selectList(null);
        for (SysUser user : userList) {
            System.out.println(user);
        }
    }

    // 生成密码
    @Test
    void getPassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s = bCryptPasswordEncoder.encode("123456");
        System.out.println(s);
    }

    // 获取IP地址
    @Test
    void getIpSource(){
        String ip = IpUtils.getHostIp();
        System.out.println("本地IP：" + ip);
        String ip2region = IpUtils.getIp2region(ip);
        System.out.println("IP归属地：" + ip2region);
        String cityInfo = IpUtils.getCityInfo(ip);
        System.out.println("省市区："+ cityInfo);
        String hostName = IpUtils.getHostName();
        System.out.println("主机名："+ hostName);
    }

    // 根据部门id查询部门信息
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Test
    void getDept(){
        SysDept dept = sysDeptMapper.findDeptById(2L);
        System.out.println(dept);
    }
}
