package cn.coisini.system.controller;

import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.log.service.AsyncLoginLogService;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.AsyncLoginLog;
import cn.coisini.model.system.vo.AsyncLoginLogQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 系统访问记录 前端控制器
 */
@RestController
@RequestMapping("/system/loginLog")
public class AsyncLoginLogController {
    @Resource
    private AsyncLoginLogService asyncLoginLogService;

    @ApiOperation("条件分页查询登录日志")
    @PreAuthorize("hasAuthority('loginLog.list')")
    @GetMapping("/list")
    public Result<AsyncLoginLog> pagingQuery(AsyncLoginLogQueryVo asyncLoginLogQueryVo) {
        return asyncLoginLogService.pagingQuery(asyncLoginLogQueryVo);
    }

    @ApiOperation("根据id查询登录日志")
    @GetMapping("/get/{id}")
    public Result<AsyncLoginLog> getLoginLog(@PathVariable("id") Long id) {
        return asyncLoginLogService.get(id);
    }

    @ApiOperation("根据id删除登录日志")
    @PreAuthorize("hasAuthority('loginLog.remove')")
    @Log(title = "登录日志管理",businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{id}")
    public Result<AsyncLoginLog> removeLoginLogById(@PathVariable("id") Long id) {
        return asyncLoginLogService.removeLoginLogById(id);
    }

    @ApiOperation("批量删除登录日志")
    @PreAuthorize("hasAuthority('loginLog.batchRemove')")
    @Log(title = "登录日志管理",businessType = BusinessType.BATCH_REMOVE)
    @DeleteMapping("/batchRemove")
    public Result<AsyncLoginLog> batchRemove(@RequestBody List<Long> ids) {
        return asyncLoginLogService.batchRemove(ids);
    }
}
