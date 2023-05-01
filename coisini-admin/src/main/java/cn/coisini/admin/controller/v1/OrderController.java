//package cn.coisini.admin.controller.v1;
//
//import cn.coisini.admin.service.OrderService;
//import cn.coisini.model.admin.pojo.Order;
//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Author: xiaoxiang
// * @Description:
// */
//@RestController
//// 当前类下的配置支持动态更新
//@RefreshScope
//@RequestMapping("/api/v1")
//public class OrderController {
//   // private final UserFeign userFeign;
//   private final OrderService orderService;
//    @Value("${config.name}")
//    String name;
//
//    public OrderController(OrderService orderService
//                           //,UserFeign userFeign
//                           ) {
//        // this.userFeign = userFeign;
//        this.orderService = orderService;
//    }
//
//    @GetMapping("/add")
//    public String add() {
//        Order order=new Order();
//        order.setProductId(9);
//        order.setStatus(0);
//        order.setTotalAmount(100);
//        orderService.create(order);
//        return "下单成功";
//    }
//
//    /**这个@SentinelResource注解用来标识资源是否被限流、降级。
//     * blockHandler: 定义当资源内部发生了BlockException应该进入的方法（捕获的是Sentinel定义的异常）
//     * fallback: 定义的是资源内部发生了Throwable应该进入的方法
//     * exceptionsToIgnore：配置fallback可以忽略的异常
//     */
//    @GetMapping("/cs")
//   /* @SentinelResource(value = "cs",
//    blockHandler = "handleException",blockHandlerClass = ExceptionUtil.class)*/
//    public String cs() {
//        return name;
//    }
//   /* public String handleException(BlockException e) {
//        return "流控";
//    }*/
//
//    // 热点规则
//    @GetMapping("/get/{id}")
//    @SentinelResource(value = "getById",blockHandler = "HotBlockHandler")
//    public String getById(@PathVariable("id") Integer id){
//        System.out.println("注册");
//        return "注册成功";
//    }
//    public String HotBlockHandler(@PathVariable("id") Integer id, BlockException e){
//        return "热点异常处理";
//    }
//
//
//    @GetMapping("/sentinel")
//    public String sentinelCS() {
//        return "sentinel控制台测试";
//    }
//}
