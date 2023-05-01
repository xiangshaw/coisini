package cn.coisini.model.system.vo;

import lombok.Data;

/**
 * @Author: xiaoxiang
 * @Description: 操作日志vo
 */
@Data
public class AsyncOperLogQueryVo {
    // 标题
    private String title;
    // 操作人员
    private String operName;
    // 开始时间
    private String createTimeBegin;
    // 结束时间
    private String createTimeEnd;
    // 当前页
    private Long current;
    // 每页几条数据
    private Long limit;
}
