package cn.coisini.system.controller.system.v1;

import cn.coisini.log.annotation.Log;
import cn.coisini.log.enums.BusinessType;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysPost;
import cn.coisini.model.system.vo.SysPostQueryVo;
import cn.coisini.system.service.SysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 岗位 前端控制器
 */
@Api(tags = "岗位管理接口")
@RestController
@RequestMapping("/api/v1/post")
public class SysPostController {
    private final SysPostService sysPostService;

    public SysPostController(SysPostService sysPostService) {
        this.sysPostService = sysPostService;
    }

    @PreAuthorize("hasAuthority('post:list')")
    @ApiOperation("岗位列表")
    @GetMapping("/list")
    public Result<SysPost> pagingQuery(SysPostQueryVo postQueryVo) {
        return sysPostService.pagingQuery(postQueryVo);
    }

    @ApiOperation("根据岗位id获取岗位")
    @GetMapping("/findPostById/{id}")
    public Result<SysPost> getPostId(@PathVariable("id") Long id) {
        return sysPostService.getPostId(id);
    }

    @PreAuthorize("hasAuthority('post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @ApiOperation("添加岗位")
    @PostMapping("/save")
    public Result<SysPost> savePost(@RequestBody SysPost sysPost) {
        return sysPostService.savePost(sysPost);
    }

    @PreAuthorize("hasAuthority('post:update')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @ApiOperation("修改岗位信息")
    @PutMapping("/update")
    public Result<SysPost> update(@RequestBody SysPost sysPost) {
        return sysPostService.updatePost(sysPost);
    }

    @PreAuthorize("hasAuthority('post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @ApiOperation("删除岗位")
    @DeleteMapping("/remove/{id}")
    public Result<SysPost> remove(@PathVariable("id") Long id) {
        return sysPostService.removePost(id);
    }

    @PreAuthorize("hasAuthority('post:batchRemove')")
    @ApiOperation("批量删除岗位")
    @Log(title = "岗位管理",businessType = BusinessType.BATCH_REMOVE)
    @DeleteMapping("/batchRemove")
    public Result<List<SysPost>> batchRemoveRole(@RequestBody List<Long> ids){
        return sysPostService.batchRemove(ids);
    }

    @ApiOperation("更改岗位状态(0启用 1禁用)")
    @Log(title = "岗位管理", businessType = BusinessType.STATUS)
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<SysPost> status(@PathVariable("id") Long id,
                                  @PathVariable("status") Boolean status) {
        return sysPostService.updateStatus(id, status);
    }
}
