package cn.coisini.log.service;

import cn.coisini.model.common.dto.Result;
import cn.coisini.model.system.pojo.AsyncLoginLog;
import cn.coisini.model.system.vo.AsyncLoginLogQueryVo;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description: 异步调用日志服务
 */
public interface AsyncLoginLogService {
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态(0成功 1失败)
     * @param loginIp ip
     * @param loginIpSource ip来源
     * @param message 消息内容
     */
    void recordLoginLog(Long userId, String username, Boolean status, String loginIp, String loginIpSource, String loginIpCity, String message);

    // 条件分页查询登录日志
    Result<AsyncLoginLog> pagingQuery(AsyncLoginLogQueryVo asyncLoginLogQueryVo);

    // 根据id查询登录日志
    Result<AsyncLoginLog> get(Long id);

    // 根据id删除登录日志
    Result<AsyncLoginLog> removeLoginLogById(Long id);
    // 批量删除登录日志
    Result<AsyncLoginLog> batchRemove(List<Long> ids);
}
