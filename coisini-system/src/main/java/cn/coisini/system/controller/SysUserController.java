package cn.coisini.system.controller;

import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysUser;
import cn.coisini.model.system.pojo.SysUserRole;
import cn.coisini.model.system.vo.AssginRoleVo;
import cn.coisini.model.system.vo.SysUserQueryVo;
import cn.coisini.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: xiaoxiang
 * @Description: 用户 前端控制器
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService userService;

    // 使用构造器注入，代替@Resource、@Autowired
    public SysUserController(SysUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('user:list')")
    @ApiOperation("用户列表")
    @GetMapping("/list")
    public Result<SysUser> pagingQuery(SysUserQueryVo userQueryVo) {
        return userService.pagingQuery(userQueryVo);
    }

    @ApiOperation("根据id获取用户")
    @GetMapping("/findUserById/{id}")
    public Result<SysUser> getUserId(@PathVariable("id") Long id) {
        return userService.getUserId(id);
    }

    @PreAuthorize("hasAuthority('user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result<SysUser> saveUser(@RequestBody SysUser sysUser) {
        return userService.saveUser(sysUser);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @ApiOperation("修改用户")
    @PostMapping("/update")
    public Result<SysUser> update(@RequestBody SysUser sysUser) {
        return userService.updateUser(sysUser);
    }

    @PreAuthorize("hasAuthority('user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result<SysUser> remove(@PathVariable("id") Long id) {
        return userService.removeUser(id);
    }

    @ApiOperation("更改用户状态(0启用 1禁用)")
    @Log(title = "用户管理", businessType = BusinessType.STATUS)
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<SysUser> status(@PathVariable("id") Long id,
                         @PathVariable("status") Boolean status) {
        return userService.updateStatus(id, status);
    }

    @ApiOperation("根据用户id获取角色信息数据")
    @GetMapping("/toAssign/{id}")
    public Result<Map<String, Object>> getRolesByUserId(@PathVariable("id") Long id) {
        return userService.getRolesByUserId(id);
    }

    @PreAuthorize("hasAuthority('user:assignRole')")
    @Log(title = "用户管理", businessType = BusinessType.CAST)
    @ApiOperation("给用户分配角色")
    @PostMapping("/doAssign")
    public Result<SysUserRole> doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        return userService.doAssign(assginRoleVo);
    }
}
