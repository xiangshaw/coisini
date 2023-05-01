package cn.coisini.system.service.impl;

import cn.coisini.common.utils.PageUtils;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.pojo.SysRole;
import cn.coisini.model.system.vo.SysRoleQueryVo;
import cn.coisini.system.mapper.SysRoleMapper;
import cn.coisini.system.service.SysRoleService;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.coisini.model.system.constants.SystemConstants.FALSE;

/**
 * @Author: xiaoxiang
 * @Description: 角色 服务实现类
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    // 根据id查询角色
    @Override
    public Result<SysRole> findRoleById(Long id) {
        // 检查id
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        SysRole sysRole = getById(id);
        // 检查返回结果
        if (sysRole == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(sysRole);
    }

    // 条件分页查询
    @Override
    public Result<SysRole> pagingQuery(SysRoleQueryVo sysRoleQueryVo) {
        // 获取条件值
        String roleName = sysRoleQueryVo.getRoleName();
        // 封装条件
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        // 根据 角色名 模糊查询
        if(!CharSequenceUtil.isEmpty(roleName)){
            wrapper.like("role_name",roleName);
        } // 排序
        wrapper.eq("del_flag",FALSE).orderByDesc("id");
        // 分页条件
        Page<SysRole> page = new Page<>(sysRoleQueryVo.getCurrent(), sysRoleQueryVo.getLimit());
        Page<SysRole> rolePage = page(page, wrapper);
        return Result.ok(new PageUtils(rolePage));

    }

    // 新增角色
    @Override
    public Result<SysRole> saveRole(SysRole sysRole) {
        // 1.检查参数
        if (sysRole == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<SysRole> list = list(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleName, sysRole.getRoleName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该角色已经添加过了");
        }
        // 3.检查结果
        boolean b = save(sysRole);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "角色添加失败");
    }

    // 修改角色
    @Override
    public Result<SysRole> updateRole(SysRole sysRole) {
        // 1.检查参数
        if (sysRole == null && sysRole.getId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.检查结果
        boolean b = updateById(sysRole);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改角色失败");
    }

    // 删除角色
    @Override
    public Result<SysRole> removeRole(Long id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前角色是否存在
        SysRole sysRole = getById(id);
        if (sysRole == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除并判断结果
        boolean b = removeById(id);
        if (b) {
            return Result.error(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "删除角色失败");
    }

    // 批量删除
    @Override
    public Result<List<SysRole>> batchRemove(List<Long> ids) {
        // 1.检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前角色是否存在 和 是否有效
        List<SysRole> sysRoleList = listByIds(ids);
        if (sysRoleList == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除并检查结果
        boolean b = removeByIds(ids);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "删除角色失败");
    }
}
