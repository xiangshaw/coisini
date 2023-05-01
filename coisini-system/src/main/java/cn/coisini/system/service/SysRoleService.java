package cn.coisini.system.service;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysRole;
import cn.coisini.model.system.vo.SysRoleQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 角色 服务类
 */
public interface SysRoleService extends IService<SysRole> {
    // 根据id查询角色
    Result<SysRole> findRoleById(Long id);
    // 条件分页查询
    Result<SysRole> pagingQuery(SysRoleQueryVo sysRoleQueryVo);
    // 新增角色
    Result<SysRole> saveRole(SysRole sysRole);
    // 修改角色
    Result<SysRole> updateRole(SysRole sysRole);
    // 删除角色
    Result<SysRole> removeRole(Long id);
    // 批量删除
    // json数组格式 ---对应---Java的list集合
    Result<List<SysRole>> batchRemove(List<Long> ids);
}
