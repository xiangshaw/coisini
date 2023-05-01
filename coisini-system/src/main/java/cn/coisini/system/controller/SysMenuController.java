package cn.coisini.system.controller;

import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysMenu;
import cn.coisini.model.system.vo.AssginMenuVo;
import cn.coisini.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 菜单 前端控制器
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    private final SysMenuService menuService;

    public SysMenuController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasAuthority('menu:list')")
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        return Result.ok(menuService.findNodes());
    }

    @ApiOperation("根据id查询菜单")
    @GetMapping("/info/{id}")
    public Result<SysMenu> info(@PathVariable("id") Long id) {
        SysMenu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    @PreAuthorize("hasAuthority('menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public Result<SysMenu> saveMenu(@RequestBody SysMenu sysMenu) {
        return menuService.saveMenu(sysMenu);
    }

    @PreAuthorize("hasAuthority('menu:update')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public Result<SysMenu> updateMenu(@RequestBody SysMenu sysMenu) {
        return menuService.updateMenuById(sysMenu);
    }

    @PreAuthorize("hasAuthority('menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @ApiOperation("根据id删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result<SysMenu> remove(@PathVariable("id") Long id) {
        return menuService.removeMenuById(id);

    }

    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result<List<SysMenu>> toAssign(@PathVariable("roleId") Long id) {
        return Result.ok(menuService.findMenuByRoleId(id));
    }

    @PreAuthorize("hasAuthority('role:assignAuth')")
    @Log(title = "菜单管理", businessType = BusinessType.ASSGIN)
    @ApiOperation("给角色分配权限")
    @PostMapping("/doAssign")
    public Result<SysMenu> doAssign(@RequestBody AssginMenuVo menuVo) {
        return menuService.doAssign(menuVo);
    }

    @ApiOperation("更改菜单状态(0启用 1禁用)")
    @Log(title = "菜单管理", businessType = BusinessType.STATUS)
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<SysMenu> status(@PathVariable("id") Long id,
                         @PathVariable("status") Boolean status) {
        return menuService.updateStatus(id, status);
    }
}
