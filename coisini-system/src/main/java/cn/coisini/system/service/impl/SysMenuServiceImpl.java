package cn.coisini.system.service.impl;

import cn.coisini.common.utils.RouterHelper;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.pojo.SysMenu;
import cn.coisini.model.system.pojo.SysRoleMenu;
import cn.coisini.model.system.pojo.SysUserRole;
import cn.coisini.model.system.vo.AssginMenuVo;
import cn.coisini.model.system.vo.RouterVo;
import cn.coisini.system.mapper.SysMenuMapper;
import cn.coisini.system.mapper.SysRoleMenuMapper;
import cn.coisini.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.coisini.model.system.constants.SystemConstants.FALSE;
import static cn.coisini.model.system.constants.SystemConstants.STATUS;

/**
 * @Author: xiaoxiang
 * @Description: 菜单 服务实现类
 */
@Service
public class SysMenuServiceImpl extends MPJBaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMenuMapper roleMenuMapper;

    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper, SysRoleMenuMapper roleMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public List<SysMenu> findNodes() {
        // 获取所有菜单
        List<SysMenu> menuList = baseMapper.selectList(null);
        // 构建树形数据
        return buildTree(menuList);
    }

    // 新增菜单
    @Override
    public Result<SysMenu> saveMenu(SysMenu sysMenu) {
        // 1.检查参数
        if (sysMenu == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 查询数据库菜单信息
        List<SysMenu> list = list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getName, sysMenu.getName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "菜单名称重复");
        }
        boolean b = save(sysMenu);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "添加菜单失败");
    }

    // 修改菜单
    @Override
    public Result<SysMenu> updateMenuById(SysMenu sysMenu) {
        // 1.检查参数
        if (sysMenu == null && sysMenu.getId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        boolean b = updateById(sysMenu);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    // 删除菜单
    @Override
    public Result<SysMenu> removeMenuById(Long id) {
        // 查询当前菜单下面是否有子菜单
        Long count = baseMapper.selectCount(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return Result.error(ResultEnum.FAIL, "删除失败：该菜单有子菜单");
        }
        baseMapper.deleteById(id);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 根据角色获取菜单
    @Override
    public List<SysMenu> findMenuByRoleId(Long id) {
        // 获取所有菜单 status = 0 列表
        List<SysMenu> allMenu = baseMapper.selectList(new QueryWrapper<SysMenu>().eq(STATUS, FALSE));
        // 根据角色id获取菜单
        List<SysRoleMenu> roleMenu = roleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        // 获取该角色已分配的所有权限id
        List<Long> roleMenuId = roleMenu.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        // 遍历权限数据处理：isSelect 如果菜单选中为true，否则false
        allMenu.forEach(menuEntity ->
                // 拿着分配菜单id 和 所有菜单比对，有相同的，让isSelect值为true（设置该权限已被分配）
                menuEntity.setSelect(roleMenuId.contains(menuEntity.getId()))
        );
        // 将权限列表转换为权限树 显示MenuHelper方法实现
        return buildTree(allMenu);
    }

    // 给角色分配权限
    @Override
    public Result<SysMenu> doAssign(AssginMenuVo menuVo) {
        // 根据角色id删除已分配的菜单权限
        roleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", menuVo.getRoleId()));
        // 遍历菜单id
        List<Long> menuIdList = menuVo.getMenuIdList();
        menuIdList.forEach(menuId -> {
            // 创建SysRoleMenu对象
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(menuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            // 添加菜单
            roleMenuMapper.insert(sysRoleMenu);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 根据用户id获取菜单权限值
    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        // 超级管理员（可操作所有内容）admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId == 1) {
            // 超级管理员查询所有权限，且状态为0，sort_value排序
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq(STATUS, FALSE).orderByAsc("sort_value"));
        } else {
            // 如果不是1，其他类型用户，查询这个用户权限
            sysMenuList = findMenuListUserId(userId);
        }
        // 构建树形数据
        List<SysMenu> menuTreeList = buildTree(sysMenuList);
        // 数据转换成前端路由格式
        return RouterHelper.buildRouters(menuTreeList);
    }

    /**
     * 如果不是1，其他类型用户，查询这个用户权限
     * 使用mybatis-plus-join，使用方法详情见https://gitee.com/best_handsome/mybatis-plus-join/wikis/%E4%BD%BF%E7%94%A8
     * 完成连表查询实现语句：
     * SELECT DISTINCT
     * m.id,m.parent_id,m.name,m.type,m.path,m.component,m.perms,m.icon,m.sort_value,m.status,m.create_time,m.update_time,m.del_flag
     * FROM sys_menu m
     * INNER JOIN sys_role_menu rm ON m.id=rm.menu_id
     * INNER JOIN sys_user_role ur ON rm.role_id=ur.role_id
     * WHERE ur.user_id=#{userId}
     * AND m.status = 0
     * AND m.del_flag = 0
     */
    private List<SysMenu> findMenuListUserId(Long userId) {
        return sysMenuMapper.selectJoinList(SysMenu.class, new MPJLambdaWrapper<SysMenu>()
                // 查询菜单全部字段
                .selectAll(SysMenu.class)
                // 参数1：参与连表的实体类class  参数2：连表的ON字段，这个属性必需是第一个参数实体类的属性 参数3：参与连表的ON的另一个实体类属性
                // 角色菜单（INNER JOIN sys_role_menu rm ON m.id=rm.menu_id）
                .leftJoin(SysRoleMenu.class, SysRoleMenu::getMenuId, SysMenu::getId)
                //用户角色（INNER JOIN sys_user_role ur ON rm.role_id=ur.role_id）
                .leftJoin(SysUserRole.class, SysUserRole::getRoleId, SysRoleMenu::getRoleId)
                // 根据用户id查询
                .eq(SysUserRole::getUserId, userId)
                // 菜单状态0
                .eq(SysMenu::getStatus, FALSE)
                // 菜单是否删除
                .eq(SysMenu::getDelFlag, FALSE));
    }

    // 根据用户id获取用户按钮权限
    @Override
    public List<String> findUserButtonList(Long userId) {
        // 超级管理员(可操作所有内容)admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId == 1) {
            // 超级管理员查询所有权限，且状态为0
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq(STATUS, FALSE).orderByAsc("sort_value"));
        } else {
            // 如果不是1，其他类型用户，查询这个用户权限
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }
        // 筛选出按钮
        return sysMenuList.stream()
                .filter(item -> item.getType() == 2)
                .map(SysMenu::getPerms)
                .collect(Collectors.toList());
    }

    // 更新菜单状态(1正常 0禁用)
    @Override
    public Result<SysMenu> updateStatus(Long id, Boolean status) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        menu.setStatus(status);
        menu.setUpdateTime(new Date());
        int i = baseMapper.updateById(menu);
        if (i == 1) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改状态失败");
    }

    /**
     * 构建树型结构
     */
    private List<SysMenu> buildTree(List<SysMenu> menuEntityList) {
        return menuEntityList.stream().filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    // 设置子菜单
                    menu.setChildren(getChildrens(menu, menuEntityList));
                    return menu;
                })
                .collect(Collectors.toList());
    }

    /**
     * 递归查找子节点
     */
    // 从根节点进行查询子节点
    // 判断id=parentid是否相同，如果相同则是子节点，进行数据封装
    private List<SysMenu> getChildrens(SysMenu menuEntity, List<SysMenu> menuList) {
        return menuList.stream()
                // 获取当前菜单id -- 获取所有菜单parentid 进行 比对
                .filter(menu -> Objects.equals(menu.getParentId(), menuEntity.getId()))
                .map(menu -> {
                    menu.setChildren(getChildrens(menu, menuList));
                    return menu;
                }).collect(Collectors.toList());
    }
}
