package cn.coisini.model.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@ApiModel(description = "分配部门")
@Data
public class AssginDeptVo {

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "部门id列表")
    private List<Long> deptIdList;
}
