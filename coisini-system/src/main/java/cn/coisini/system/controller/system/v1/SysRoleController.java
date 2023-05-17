package cn.coisini.system.controller.system.v1;

import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysRole;
import cn.coisini.model.system.vo.SysRoleQueryVo;
import cn.coisini.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 角色 前端控制器
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/api/v1/role")
public class SysRoleController {
    private final SysRoleService roleService;

    public SysRoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("根据id查询角色")
    @GetMapping("/info/{id}")
    public Result<SysRole> findRoleById(@PathVariable("id") Long id){
        return roleService.findRoleById(id);
    }

    // 分页查询角色  当前页 - 每页记录数 - 查询对象
    /*
     Spring Security默认是禁用注解的，要想开启注解，
     需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解，
     来判断用户对某个控制层的方法是否具有访问权限
     */
    @PreAuthorize("hasAuthority('role:list')")
    @ApiOperation("条件分页查询")
    @GetMapping("/list")
    public Result<SysRole> pagingQuery(
                       SysRoleQueryVo sysRoleQueryVo){
        return roleService.pagingQuery(sysRoleQueryVo);
    }

    @PreAuthorize("hasAuthority('role:add')")
    @Log(title = "角色管理",businessType = BusinessType.INSERT)
    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result<SysRole> saveRole(@RequestBody SysRole sysRole){
       return roleService.saveRole(sysRole);
    }

    @PreAuthorize("hasAuthority('role:update')")
    @Log(title = "角色管理",businessType = BusinessType.UPDATE)
    @ApiOperation("修改角色")
    @PutMapping("/update")
    public Result<SysRole> updateRole(@RequestBody SysRole sysRole){
       return roleService.updateRole(sysRole);
    }

    @PreAuthorize("hasAuthority('role:remove')")
    @Log(title = "角色管理",businessType = BusinessType.DELETE)
    @ApiOperation("删除角色")
    @DeleteMapping("/remove/{id}")
    public Result<SysRole> removeRole(@PathVariable("id") Long id){
        return roleService.removeRole(id);
    }

    @ApiOperation("批量删除角色")
    @Log(title = "角色管理",businessType = BusinessType.BATCH_REMOVE)
    @DeleteMapping("/batchRemove")
    public Result<List<SysRole>> batchRemoveRole(@RequestBody List<Long> ids){
       return roleService.batchRemove(ids);
    }
}
