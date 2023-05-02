package cn.coisini.system.service.impl;

import cn.coisini.common.exception.CoisiniException;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.pojo.SysPost;
import cn.coisini.model.system.pojo.SysUserPost;
import cn.coisini.model.system.vo.SysPostQueryVo;
import cn.coisini.system.mapper.SysDeptMapper;
import cn.coisini.system.mapper.SysPostMapper;
import cn.coisini.system.mapper.SysUserPostMapper;
import cn.coisini.system.service.SysPostService;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: xiaoxiang
 * @Description: 岗位 服务实现类
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final SysDeptMapper sysDeptMapper;
    private final SysUserPostMapper sysUserPostMapper;

    public SysPostServiceImpl(SysDeptMapper sysDeptMapper, SysUserPostMapper sysUserPostMapper) {
        this.sysDeptMapper = sysDeptMapper;
        this.sysUserPostMapper = sysUserPostMapper;
    }

    // 分页条件查询岗位
    @Override
    public Result<SysPost> pagingQuery(SysPostQueryVo postQueryVo) {
        String keyword = postQueryVo.getKeyword();
        String createTimeBegin = postQueryVo.getCreateTimeBegin();
        String createTimeEnd = postQueryVo.getCreateTimeEnd();
        QueryWrapper<SysPost> wrapper = new QueryWrapper<>();
        // 根据岗位名称、大于等于开始时间、小于等于创建时间
        if (CharSequenceUtil.isNotBlank(keyword)) {
            wrapper.like("post_name", postQueryVo.getKeyword());
        }
        if (CharSequenceUtil.isNotBlank(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (CharSequenceUtil.isNotBlank(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }
        wrapper.eq("del_flag", 0).orderByDesc("id");
        Page<SysPost> page = new Page<>(postQueryVo.getCurrent(), postQueryVo.getLimit());
        Page<SysPost> postPage = page(page, wrapper);
        return Result.ok(postPage);
    }

    // 根据岗位id获取岗位
    @Override
    public Result<SysPost> getPostId(Long postId) {
        // 1.检查参数
        if (postId == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.获取
        SysPost sysPost = getById(postId);
        // 3.是否为空？
        if (sysPost == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(sysPost);
    }

    // 保存岗位
    @Override
    public Result<SysPost> savePost(SysPost sysPost) {
        // 1.检查参数
        if (sysPost == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.检查岗位编码是否唯一
        List<SysPost> list = list(Wrappers.<SysPost>lambdaQuery().eq(SysPost::getPostCode, sysPost.getPostCode()));
        if (list != null && list.size() == 1){
            return Result.error(ResultEnum.DATA_EXIST,"岗位编码冲突");
        }
        // 3.保存
        boolean b = save(sysPost);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "岗位添加失败");
    }

    // 修改岗位
    @Override
    public Result<SysPost> updatePost(SysPost sysPost) {
        // 1.检查参数
        if (sysPost == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.修改岗位
        boolean b = updateById(sysPost);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    // 删除岗位
    @Override
    public Result<SysPost> removePost(Long id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断岗位是否存在，且状态是否已停用
        SysPost sysPost = getById(id);
        if (sysPost == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        if (Boolean.FALSE.equals(sysPost.getStatus())) {
            return Result.error(ResultEnum.FAIL, "请先禁用岗位后再尝试");
        }
        // 3.删除并判断结果
        boolean b = removeById(id);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "岗位删除失败");
    }

    // 批量删除（待优化）
    @Override
    public Result<List<SysPost>> batchRemove(List<Long> ids) {
        // 1.检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断岗位是否存在并且是否被分配
        List<SysPost> postList = listByIds(ids);
        if (postList == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 统计岗位是否分配
        for (Long id : ids) {
            // 根据id查询岗位信息
            Result<SysPost> post = getPostId(id);
            if (countUserPostById(id) > 0) {
                Result.error(ResultEnum.FAIL,"岗位已经分配，不能删除");
                throw new CoisiniException(201, String.format("%1$s已分配,不能删除", post.getData().getPostName()));
            }
        }
        List<SysPost> posts = postList.stream()
                .filter(Objects::nonNull)
                .filter(post -> post.getStatus().equals(Boolean.TRUE))
                .collect(Collectors.toList());
        // 3.为空 表示 没有状态可用的岗位数据
        if (posts.isEmpty()) {
            boolean b = removeByIds(ids);
            if (b) {
                return Result.ok(ResultEnum.SUCCESS);
            }
            return Result.error(ResultEnum.FAIL, "岗位可用，删除失败");
        }
        return Result.error(ResultEnum.FAIL, "岗位可用，删除失败");
    }

    // 查询岗位已经分配的数量
    @Override
    public Long countUserPostById(Long postId) {
        QueryWrapper<SysUserPost> wrapper = new QueryWrapper<>();
        wrapper.eq("post_id", postId);
        return sysUserPostMapper.selectCount(wrapper);
    }

    // 修改岗位状态
    @Override
    public Result<SysPost> updateStatus(Long id, Boolean status) {
        // 1.检查参数
        if (id == null && status == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据岗位id查询
        SysPost post = getById(id);
        // 3.设置状态值
        post.setStatus(status);
        post.setUpdateTime(new Date());
        // 4.修改并判断结果
        boolean b = updateById(post);
        if (b){
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL,"修改失败");
    }
}
