package cn.coisini.system.service;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysMenu;
import cn.coisini.model.system.vo.AssginMenuVo;
import cn.coisini.model.system.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 菜单 服务类
 */
public interface SysMenuService extends IService<SysMenu> {
    // 菜单列表 菜单树形数据
    List<SysMenu> findNodes();
    // 新增菜单
    Result<SysMenu> saveMenu(SysMenu sysMenu);
    // 修改菜单
    Result<SysMenu> updateMenuById(SysMenu sysMenu);
    // 根据id删除菜单，(如果有子菜单不能删除)
    Result<SysMenu> removeMenuById(Long id);
    // 根据角色id获取菜单
    List<SysMenu> findMenuByRoleId(Long id);
    // 给角色分配菜单权限
    Result<SysMenu> doAssign(AssginMenuVo menuVo);
    // 根据用户id查询菜单权限值
    List<RouterVo> findUserMenuList(Long userId);
    // 根据用户id查询按钮权限值
    List<String> findUserButtonList(Long userId);
    // 更新菜单状态(0正常 1禁用)
    Result<SysMenu> updateStatus(Long id, Boolean status);
}
