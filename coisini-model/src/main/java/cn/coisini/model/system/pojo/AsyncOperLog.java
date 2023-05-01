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
 * @Description: 操作日志记录
 */
@TableName("sys_oper_log")
@ApiModel(value = "AsyncOperLog", description = "操作日志记录")
public class AsyncOperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日志主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("title")
    @ApiModelProperty("模块标题")
    private String title;

    @TableField("business_type")
    @ApiModelProperty("业务类型（0其它 1新增 2修改 3删除）")
    private String businessType;

    @TableField("method")
    @ApiModelProperty("方法名称")
    private String method;

    @TableField("request_method")
    @ApiModelProperty("请求方式")
    private String requestMethod;

    @TableField("operator_type")
    @ApiModelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    private String operatorType;

    @TableField("oper_user_id")
    @ApiModelProperty("操作人员ID")
    private Long operUserId;

    @TableField("oper_name")
    @ApiModelProperty("操作人员")
    private String operName;

    @TableField("dept_name")
    @ApiModelProperty("部门名称")
    private String deptName;

    @TableField("oper_url")
    @ApiModelProperty("请求URL")
    private String operUrl;

    @TableField("oper_ip")
    @ApiModelProperty("主机IP")
    private String operIp;

    @TableField("oper_ip_source")
    @ApiModelProperty("主机地址")
    private String operIpSource;
    @TableField("oper_ip_city")
    @ApiModelProperty("IP 省市区")
    private String operIpCity;

    @TableField("oper_param")
    @ApiModelProperty("请求参数")
    private String operParam;

    @TableField("json_result")
    @ApiModelProperty("返回参数")
    private String jsonResult;

    @TableField("status")
    @ApiModelProperty("操作状态（0正常 1异常）")
    private Boolean status;

    @TableField("error_msg")
    @ApiModelProperty("错误消息")
    private String errorMsg;

    @TableField("oper_time")
    @ApiModelProperty("操作时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date operTime;

    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Integer delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public Long getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(Long operUserId) {
        this.operUserId = operUserId;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }
    public String getOperIp() {
        return operIp;
    }
    public String getOperIpSource() {
        return operIpSource;
    }

    public void setOperIpSource(String operIpSource) {
        this.operIpSource = operIpSource;
    }
    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }
    public String getOperParam() {
        return operParam;
    }

    public String getOperIpCity() {
        return operIpCity;
    }

    public void setOperIpCity(String operIpCity) {
        this.operIpCity = operIpCity;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }
    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
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
        return "AsyncOperLog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", businessType='" + businessType + '\'' +
                ", method='" + method + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", operatorType='" + operatorType + '\'' +
                ", operUserId=" + operUserId +
                ", operName='" + operName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", operUrl='" + operUrl + '\'' +
                ", operIp='" + operIp + '\'' +
                ", operIpSource='" + operIpSource + '\'' +
                ", operIpCity='" + operIpCity + '\'' +
                ", operParam='" + operParam + '\'' +
                ", jsonResult='" + jsonResult + '\'' +
                ", status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", operTime=" + operTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
