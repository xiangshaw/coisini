package cn.coisini.system.service.impl;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.pojo.SysDept;
import cn.coisini.model.system.pojo.SysRoleDept;
import cn.coisini.model.system.vo.AssginDeptVo;
import cn.coisini.system.mapper.SysDeptMapper;
import cn.coisini.system.mapper.SysRoleDeptMapper;
import cn.coisini.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.coisini.model.system.constants.SystemConstants.FALSE;
import static cn.coisini.model.system.constants.SystemConstants.STATUS;

/**
 * @Author: xiaoxiang
 * @Description: 部门 服务实现类
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;
    private final SysRoleDeptMapper sysRoleDeptMapper;

    public SysDeptServiceImpl(SysDeptMapper sysDeptMapper, SysRoleDeptMapper sysRoleDeptMapper) {
        this.sysDeptMapper = sysDeptMapper;
        this.sysRoleDeptMapper = sysRoleDeptMapper;
    }

    // 部门列表 菜单树形数据
    @Override
    public List<SysDept> findNodes() {
        // 获取所有部门
        List<SysDept> deptList = sysDeptMapper.selectList(null);
        // 构建树形数据
        return buildDeptTree(deptList);
    }

    // 新增部门
    @Override
    public Result<SysDept> saveDept(SysDept sysDept) {
        // 1.检查参数
        if (sysDept == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断部门是否添加过
        List<SysDept> list = list(Wrappers.<SysDept>lambdaQuery().eq(SysDept::getDeptName, sysDept.getDeptName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该部门已存在");
        }
        // 子节点
        SysDept dept = getById(sysDept.getParentId());
        sysDept.setTreePath(dept.getTreePath() + "," + dept.getId());
        sysDept.setCreateTime(new Date());
        // 3.保存
        boolean b = save(sysDept);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "新增部门失败");
    }

    // 修改部门
    @Override
    public Result<SysDept> updateDeptById(SysDept sysDept) {
        // 1.检查参数
        if (sysDept.getId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        Long deptParentId = sysDept.getParentId();
        if (deptParentId == null) {
            boolean bUpdateById = updateById(sysDept);
            if (bUpdateById) {
                return Result.ok(ResultEnum.SUCCESS);
            }
            return Result.error(ResultEnum.FAIL, "修改失败");
        }
        // 如果是首个节点
        if (deptParentId == 0) {
            updateById(sysDept);
            return Result.ok(ResultEnum.SUCCESS);
        }
        SysDept parentId = getById(sysDept.getParentId());
        if (parentId == null) {
            updateById(sysDept);
            return Result.ok(ResultEnum.SUCCESS);
        }
        String newTreePath = parentId.getTreePath() + "," + parentId.getId();
        sysDept.setTreePath(newTreePath);
        boolean b = updateById(sysDept);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改部门失败");
    }

    // 删除部门
    @Override
    public Result<SysDept> removeDeptById(Long id) {
        // 查询当前部门下面是否有子部门
        Long count = sysDeptMapper.selectCount(new QueryWrapper<SysDept>().eq("parent_id", id));
        if (count > 0) {
            return Result.error(ResultEnum.FAIL, "删除失败：该部门下有子部门");
        }
        sysDeptMapper.deleteById(id);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 根据角色id获取部门信息
    @Override
    public List<SysDept> findDeptByRoleId(Long roleId) {
        // 获取所有状态正常的部门
        List<SysDept> allDept = sysDeptMapper.selectList(new QueryWrapper<SysDept>().eq(STATUS, FALSE));
        // 根据角色id获取部门
        List<SysRoleDept> roleDept = sysRoleDeptMapper.selectList(new QueryWrapper<SysRoleDept>().eq("role_id", roleId));
        // 获取该角色已分配的所有部门id
        List<Long> roleDeptId = roleDept.stream().map(SysRoleDept::getDeptId).collect(Collectors.toList());
        // 遍历权限数据处理：isSelect 如果部门选中为true，否则false
        allDept.forEach(dept ->
                // 拿着分配部门id 和 所有部门比对，有相同的，让isSelect值为true（设置该部门已被分配）
                dept.setSelect(roleDeptId.contains(dept.getId()))
        );
        // 将部门列表转换为权限树
        return buildDeptTree(allDept);
    }

    // 给角色分配部门
    @Override
    public Result<SysRoleDept> doAssign(AssginDeptVo deptVo) {
        // 根据角色id删除已分配的部门
        sysRoleDeptMapper.delete(new QueryWrapper<SysRoleDept>().eq("role_id", deptVo.getRoleId()));
        // 遍历部门id
        List<Long> deptIdList = deptVo.getDeptIdList();
        deptIdList.forEach(deptId -> {
            // 创建SysRoleDept对象
            SysRoleDept sysRoleDept = new SysRoleDept();
            sysRoleDept.setRoleId(deptVo.getRoleId());
            sysRoleDept.setDeptId(deptId);
            // 添加部门
            sysRoleDeptMapper.insert(sysRoleDept);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }

    @Override
    public Result<SysDept> updateStatus(Long id, Boolean status) {
        SysDept dept = new SysDept();
        dept.setId(id);
        dept.setStatus(status);
        dept.setUpdateTime(new Date());
        int i = baseMapper.updateById(dept);
        if (i == 1) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改状态失败");
    }

    /**
     * 构建树型结构
     */
    private List<SysDept> buildDeptTree(List<SysDept> deptList) {
        return deptList.stream().filter(dept -> dept.getParentId() == 0)
                .map(dept -> {
                    // 设置子部门
                    dept.setChildren(getChildrens(dept, deptList));
                    return dept;
                })
                .collect(Collectors.toList());
    }

    /**
     * 递归查找子节点
     */
    // 从根节点进行查询子节点
    // 判断id=parentid是否相同，如果相同则是子节点，进行数据封装
    private List<SysDept> getChildrens(SysDept sysDept, List<SysDept> deptList) {
        return deptList.stream()
                // 获取当前部门id -- 获取所有部门parentid 进行 比对
                .filter(dept -> Objects.equals(dept.getParentId(), sysDept.getId()))
                .map(dept -> {
                    dept.setChildren(getChildrens(dept, deptList));
                    return dept;
                }).collect(Collectors.toList());
    }
}
