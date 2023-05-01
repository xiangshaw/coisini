package cn.coisini.model.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xiaoxiang
 * @Description: 用户认证
 */
@Data
@ApiModel(description = "管理员用户认证")
@TableName("ad_user_auth")
public class AdUserAuth implements Serializable {
    // 主键
    // 描述主键为id 主键自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 用户信息ID
    @TableField("user_info_id")
    private Long userInfoId;
    // 登录用户名(唯一)
    @TableField("username")
    private String username;
    // 登录密码
    @TableField("password")
    private String password;
    // 盐
    @TableField("salt")
    private String salt;
    // 登录类型
    @TableField("login_type")
    private String loginType;
    // 登录IP
    @TableField("login_ip")
    private String loginIp;
    // IP来源
    @TableField("ip_source")
    private String ipSource;
    // 创建时间
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    // 修改时间
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    // 最后一次登录时间
    @TableField("login_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date loginTime;

}
