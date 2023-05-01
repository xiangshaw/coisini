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
import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 菜单
 */
@TableName("sys_menu")
@ApiModel(value = "SysMenu", description = "菜单")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("编号")
    private Long id;

    @TableField("parent_id")
    @ApiModelProperty("所属上级")
    private Long parentId;

    @TableField("name")
    @ApiModelProperty("名称")
    private String name;

    @TableField("type")
    @ApiModelProperty("类型(0目录 1菜单 2按钮)")
    private Integer type;

    @TableField("path")
    @ApiModelProperty("路由地址")
    private String path;

    @TableField("component")
    @ApiModelProperty("组件路径")
    private String component;

    @TableField("perms")
    @ApiModelProperty("权限标识")
    private String perms;

    @TableField("icon")
    @ApiModelProperty("图标")
    private String icon;

    @TableField("sort_value")
    @ApiModelProperty("排序")
    private Integer sortValue;

    @TableField("status")
    @ApiModelProperty("状态(0:禁止,1:正常)")
    private Boolean status;

    @TableField("create_time")
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Integer delFlag;

    @TableField(exist = false)
    private List<SysMenu> children;

    @TableField(exist = false)
    private boolean isSelect;

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortValue() {
        return sortValue;
    }

    public void setSortValue(Integer sortValue) {
        this.sortValue = sortValue;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name=" + name +
                ", type=" + type +
                ", path=" + path +
                ", component=" + component +
                ", perms=" + perms +
                ", icon=" + icon +
                ", sortValue=" + sortValue +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                "}";
    }
}
