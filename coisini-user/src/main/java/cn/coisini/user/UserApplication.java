package cn.coisini.user;

import cn.coisini.common.utils.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@SpringBootApplication
// 服务注册发现
@EnableDiscoveryClient
//开启 OpenFeign 功能
@EnableFeignClients
public class UserApplication {
    @Value("${workId}")
    private int workId;
    @Value("${datacenterId}")
    private int datacenterId;
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
    // 使用分布式ID
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workId,datacenterId);
    }
    // 分页插件
    @Bean
    public MybatisPlusInterceptor paginationInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }
}
