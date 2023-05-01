package cn.coisini.model.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xiaoxiang
 * @Description: 管理员用户信息
 */
@Data
@ApiModel(description = "管理员用户信息")
@TableName("ad_user_info")
public class AdUserInfo implements Serializable {
    // 主键
    // 描述主键为id 主键自增
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 邮箱
    @TableField("email")
    private String email;
    // 昵称
    @TableField("nickname")
    private String nickName;
    // 描述
    @TableField("description")
    private String description;
    // 手机号码
    @TableField("phone")
    private String phone;
    // 头像地址
    @TableField("avatar")
    private String avatar;
    // 个人网站
    @TableField("web_site")
    private String webSite;
    /**
     * 性别
     * 0 男
     * 1 女
     */
    @TableField("sex")
    private Boolean sex;
    /**
     * 状态
     * 0 暂时不可用
     * 1 永久不可用
     * 9 正常可用
     */
    @TableField("status")
    private Boolean status;
    // 创建时间
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    // 修改时间
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    /**
     * 删除标记
     * 0 正常（默认）
     * 1 删除
     */
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;
}
