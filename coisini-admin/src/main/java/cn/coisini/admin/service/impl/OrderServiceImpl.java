//package cn.coisini.admin.service.impl;
//
//import cn.coisini.admin.feign.UserFeign;
//import cn.coisini.admin.mapper.OrderMapper;
//import cn.coisini.admin.service.OrderService;
//import cn.coisini.model.admin.pojo.Order;
//import io.seata.spring.annotation.GlobalTransactional;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.util.Objects;
//
///**
// * @Author: xiaoxiang
// * @Description:
// */
//@Service
//public class OrderServiceImpl implements OrderService {
//    OrderMapper orderMapper;
//    UserFeign userFeign;
//
//    /**
//     * 下单
//     * @return
//     * 被全局异常捕获，不会进行回滚，需要进行异常抛出
//     */
//    // @Transactional
//    @GlobalTransactional(rollbackFor = Exception.class) // 遇到Exception都回滚
//    @Override
//    // @Trace // 链路追踪
//   /* @Tags({@Tag(key = "param", value = "arg[0]")})
//    @Tag(key = "user",value = "returnedObj")*/
//    public Order create(Order order) {
//        // 插入能否成功？
//        orderMapper.insert(order);
//        // 扣减库存 能否成功？
//        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
//        paramMap.add("productId", order.getProductId());
//        userFeign.reduct((Integer) Objects.requireNonNull(paramMap.get("productId")).get(0));
//        // 异常
//        int a=1/0;
//        return order;
//    }
//}
