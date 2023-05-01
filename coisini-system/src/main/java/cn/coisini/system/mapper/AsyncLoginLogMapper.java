package cn.coisini.system.mapper;

import cn.coisini.model.system.pojo.AsyncLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xiaoxiang
 * @Description: 系统访问记录
 */
@Mapper
public interface AsyncLoginLogMapper extends BaseMapper<AsyncLoginLog> {
}
