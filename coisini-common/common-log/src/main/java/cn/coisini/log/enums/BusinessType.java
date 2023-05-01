package cn.coisini.log.enums;

/**
 * @Author: xiaoxiang
 * @Description: 业务操作类型
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 批量删除
     */
    BATCH_REMOVE,

    /**
     * 授权
     */
    ASSGIN,

    /**
     * 分配角色
     */
    CAST,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 更新状态
     */
    STATUS,

    /**
     * 清空数据
     */
    CLEAN,
}