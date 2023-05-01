package cn.coisini.model.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xiaoxiang
 * @Description: 用户查询实体
 */
@Data
public class SysUserQueryVo implements Serializable {

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
    // 角色id
    private Long roleId;
    // 岗位id
    private Long postId;
    // 部门id
    private Long deptId;

}
