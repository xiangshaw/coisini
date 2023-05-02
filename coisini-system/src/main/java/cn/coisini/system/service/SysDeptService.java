package cn.coisini.system.service;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysDept;
import cn.coisini.model.system.vo.AssginMenuVo;
import cn.coisini.model.system.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 部门 服务类
 */
public interface SysDeptService extends IService<SysDept> {
    // 部门列表 菜单树形数据
    List<SysDept> findNodes();
    // 新增部门
    Result<SysDept> saveDept(SysDept sysDept);
    // 修改部门
    Result<SysDept> updateDeptById(SysDept sysDept);
    // 根据id删除部门，(如果有子部门不能删除)
    Result<SysDept> removeDeptById(Long id);
    // 根据岗位id获取部门
    List<SysDept> findDeptByRoleId(Long id);
    // 给岗位分配部门
    Result<SysDept> doAssign(AssginMenuVo menuVo);
    // 根据用户id查询部门
    List<RouterVo> findUserDeptList(Long userId);
    // 根据用户id查询岗位
    List<String> findUserPostList(Long userId);
    // 更新部门状态(0正常 1禁用)
    Result<SysDept> updateStatus(Long id, Boolean status);
}
