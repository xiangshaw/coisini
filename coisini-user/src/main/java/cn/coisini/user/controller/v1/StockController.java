package cn.coisini.user.controller.v1;

import cn.coisini.user.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@RestController
@RequestMapping("/api/v1")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("/reduct/{productId}")
    public String reduct(@PathVariable("productId") Integer productId){
        stockService.reduct(productId);
        return "扣减库存";
    }
}
