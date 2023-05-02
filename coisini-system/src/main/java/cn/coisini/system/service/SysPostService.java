package cn.coisini.system.service;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.SysPost;
import cn.coisini.model.system.vo.SysPostQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 岗位信息 服务类
 */
public interface SysPostService extends IService<SysPost> {
    // 分页条件查询岗位
    Result<SysPost> pagingQuery(SysPostQueryVo postQueryVo);

    // 根据岗位id获取岗位
    Result<SysPost> getPostId(Long postId);

    // 保存岗位
    Result<SysPost> savePost(SysPost sysPost);

    // 修改岗位
    Result<SysPost> updatePost(SysPost sysPost);

    // 删除岗位
    Result<SysPost> removePost(Long id);

    // 批量删除
    // json数组格式 ---对应---Java的list集合
    Result<List<SysPost>> batchRemove(List<Long> ids);

    // 查询岗位用户数量
    Long countUserPostById(Long postId);

    // 修改岗位状态（0正常 1禁用）
    Result<SysPost> updateStatus(Long id, Boolean status);


}
