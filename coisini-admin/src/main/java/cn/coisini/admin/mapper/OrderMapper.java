package cn.coisini.admin.mapper;

import cn.coisini.model.admin.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    int insert(Order record);
}
