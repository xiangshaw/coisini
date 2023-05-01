package cn.coisini.model.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@Data
public class AsyncLoginLogQueryVo {
    @ApiModelProperty(value = "用户账号")
    private String username;
    // 开始时间
    private String createTimeBegin;
    // 结束时间
    private String createTimeEnd;
    // 当前页
    private Long current;
    // 每页几条数据
    private Long limit;
}
