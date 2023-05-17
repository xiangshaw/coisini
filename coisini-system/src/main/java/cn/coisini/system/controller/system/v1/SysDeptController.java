package cn.coisini.system.controller.system.v1;

import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysDept;
import cn.coisini.model.system.pojo.SysRoleDept;
import cn.coisini.model.system.vo.AssginDeptVo;
import cn.coisini.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 部门 前端控制器
 */
@Api(tags = "部门管理接口")
@RestController
@RequestMapping("/api/v1/dept")
public class SysDeptController {
    private final SysDeptService deptService;

    public SysDeptController(SysDeptService deptService) {
        this.deptService = deptService;
    }
    @PreAuthorize("hasAuthority('dept:list')")
    @ApiOperation("部门列表")
    @GetMapping("/findNodes")
    public Result<List<SysDept>> findNodes() {
        return Result.ok(deptService.findNodes());
    }

    @ApiOperation("根据id查询部门")
    @GetMapping("/findDeptById/{id}")
    public Result<SysDept> findDept(@PathVariable("id") Long id) {
        return Result.ok( deptService.getById(id));
    }

    @PreAuthorize("hasAuthority('dept:add')")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @ApiOperation("添加部门")
    @PostMapping("/save")
    public Result<SysDept> saveDept(@RequestBody SysDept sysDept) {
        return deptService.saveDept(sysDept);
    }

    @PreAuthorize("hasAuthority('dept:update')")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @ApiOperation("修改部门")
    @PutMapping("/update")
    public Result<SysDept> updateDept(@RequestBody SysDept sysDept) {
        return deptService.updateDeptById(sysDept);
    }

    @PreAuthorize("hasAuthority('dept:remove')")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @ApiOperation("根据id删除部门")
    @DeleteMapping("/remove/{id}")
    public Result<SysDept> remove(@PathVariable("id") Long id) {
        return deptService.removeDeptById(id);

    }

    @ApiOperation("根据角色获取部门")
    @GetMapping("/toAssign/{roleId}")
    public Result<List<SysDept>> toAssign(@PathVariable("roleId") Long id) {
        return Result.ok(deptService.findDeptByRoleId(id));
    }

    @PreAuthorize("hasAuthority('dept:assignAuth')")
    @Log(title = "部门管理", businessType = BusinessType.ASSGIN)
    @ApiOperation("给角色分配部门")
    @PostMapping("/doAssign")
    public Result<SysRoleDept> doAssign(@RequestBody AssginDeptVo deptVo) {
        return deptService.doAssign(deptVo);
    }

    @ApiOperation("更改部门状态(0启用 1禁用)")
    @Log(title = "部门管理", businessType = BusinessType.STATUS)
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<SysDept> status(@PathVariable("id") Long id,
                                  @PathVariable("status") Boolean status) {
        return deptService.updateStatus(id, status);
    }
}
