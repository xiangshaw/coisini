package cn.coisini.system.service;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysUser;
import cn.coisini.model.system.pojo.SysUserRole;
import cn.coisini.model.system.vo.AssginRoleVo;
import cn.coisini.model.system.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Author: xiaoxiang
 * @Description: 用户 服务类
 */
public interface SysUserService extends IService<SysUser> {

    // 分页条件查询用户
    Result<SysUser> pagingQuery(SysUserQueryVo userQueryVo);

    // 根据id获取用户
    Result<SysUser> getUserId(Long id);

    // 保存用户
    Result<SysUser> saveUser(SysUser sysUser);

    // 修改用户
    Result<SysUser> updateUser(SysUser sysUser);

    // 删除用户
    Result<SysUser> removeUser(Long id);
    // 批量删除？考虑考虑

    // 修改用户状态（0正常 1禁用）
    Result<SysUser> updateStatus(Long id, Boolean status);

    // ---用户 角色 关系---
    // 根据用户id获取角色信息
    Result<Map<String, Object>> getRolesByUserId(Long id);
    // 给用户分配角色
    Result<SysUserRole> doAssign(AssginRoleVo assginRoleVo);

    // 权限认证
    // 用户登录（根据用户名称进行查询）
    SysUser getUserInfoByName(String username);

    // 获取用户信息以及权限数据
    Map<String, Object> getUserInfo(Long userId,String username);

    // 用户注册
    Result<SysUser> registerUser(SysUser sysUser);
}
