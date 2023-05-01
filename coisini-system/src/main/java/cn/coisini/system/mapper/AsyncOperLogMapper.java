package cn.coisini.system.mapper;

import cn.coisini.model.system.pojo.AsyncOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xiaoxiang
 * @Description: 操作日志记录
 */
@Mapper
public interface AsyncOperLogMapper extends BaseMapper<AsyncOperLog> {
}
