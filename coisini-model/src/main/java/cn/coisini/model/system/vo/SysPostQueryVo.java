package cn.coisini.model.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xiaoxiang
 * @Description: 岗位查询实体
 */
@Data
public class SysPostQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;
    // 当前页
    private Long current;
    // 每页几条数据
    private Long limit;
    // 关键词
    private String keyword;
    // 开始时间
    private String createTimeBegin;
    // 结束时间
    private String createTimeEnd;
    // 部门id
    private Long deptId;
    // 岗位id
    private Long postId;
    // 用户id
    private Long userId;
}
