package cn.coisini.model.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xiaoxiang
 * @Description: 角色查询实体
 */
@Data
public class SysRoleQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "roleName", value = "角色名称", required = false)
    private String roleName;
    // 当前页
    private Long current;
    // 每页几条数据
    private Long limit;
}
