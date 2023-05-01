package cn.coisini.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: xiaoxiang
 * @Description: 用户远程接口
 */
@FeignClient(value = "user",path = "/api/v1/user")
//@FeignClient(value = "user",path = "/api/user",fallback = UserFeignServiceFallback.class)
public interface UserFeign {
    @GetMapping("/reduct/{productId}")
    public String reduct(@PathVariable("productId") Integer productId);
}
