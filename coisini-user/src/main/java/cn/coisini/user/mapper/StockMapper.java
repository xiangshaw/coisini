package cn.coisini.user.mapper;

import cn.coisini.model.user.pojo.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    Stock selectByPrimaryKey(Integer id);

    List<Stock> selectAll();

    int updateByPrimaryKey(Stock record);

    void reduct(Integer productId);
}
