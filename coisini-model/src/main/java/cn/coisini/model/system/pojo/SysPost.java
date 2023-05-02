package cn.coisini.model.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xiaoxiang
 * @Description: 岗位信息
 */
@TableName("sys_post")
@ApiModel(value = "SysPost", description = "岗位信息")
public class SysPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("岗位ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("post_code")
    @ApiModelProperty("岗位编码")
    private String postCode;

    @TableField("post_name")
    @ApiModelProperty("岗位名称")
    private String postName;

    @TableField("description")
    @ApiModelProperty("描述")
    private String description;

    @TableField("sort_value")
    @ApiModelProperty("排序")
    private String sortValue;

    @TableField("create_by")
    @ApiModelProperty("创建者")
    private String createBy;

    @TableField("status")
    @ApiModelProperty("状态（0正常 1停用）")
    private Boolean status;

    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0:可用 1:已删除）")
    private Boolean delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysPost{" +
                "id=" + id +
                ", postCode='" + postCode + '\'' +
                ", postName='" + postName + '\'' +
                ", description='" + description + '\'' +
                ", sortValue='" + sortValue + '\'' +
                ", createBy='" + createBy + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
