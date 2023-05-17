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
 * @Description: 系统访问记录
 */
@TableName("sys_login_log")
@ApiModel(value = "AsyncLoginLog", description = "系统访问记录")
public class AsyncLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("访问ID")
    private Long id;

    @TableField("username")
    @ApiModelProperty("用户名称")
    private String username;

    @TableField("user_id")
    @ApiModelProperty("用户ID")
    private Long userId;

    @TableField("login_ip")
    @ApiModelProperty("登录IP")
    private String loginIp;

    @TableField("login_ip_source")
    @ApiModelProperty("登录IP地址")
    private String loginIpSource;

    @TableField("login_ip_city")
    @ApiModelProperty("登录IP省市区")
    private String loginIpCity;

    @TableField("status")
    @ApiModelProperty("登录状态（0成功 1失败）")
    private Boolean status;

    @TableField("msg")
    @ApiModelProperty("提示信息")
    private String msg;

    @TableField("access_time")
    @ApiModelProperty("访问时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date accessTime;

    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Boolean delFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginIpSource() {
        return loginIpSource;
    }

    public void setLoginIpSource(String loginIpSource) {
        this.loginIpSource = loginIpSource;
    }

    public String getLoginIpCity() {
        return loginIpCity;
    }

    public void setLoginIpCity(String loginIpCity) {
        this.loginIpCity = loginIpCity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
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
        return "AsyncLoginLog{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", loginIp='" + loginIp + '\'' +
                ", loginIpSource='" + loginIpSource + '\'' +
                ", loginIpCity='" + loginIpCity + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", accessTime=" + accessTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
