package cn.coisini.user.service.Impl;

import cn.coisini.user.mapper.StockMapper;
import cn.coisini.user.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockMapper stockMapper;

    @Override
    public void reduct(Integer productId) {
        System.out.println("更新商品:"+productId);
        stockMapper.reduct(productId);
    }
}
