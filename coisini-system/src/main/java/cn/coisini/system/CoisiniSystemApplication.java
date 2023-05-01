package cn.coisini.system;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: xiaoxiang
 * @Description: CoisiniSystem 启动类
 */
@SpringBootApplication
@ComponentScan("cn.coisini")
// 开启服务注册发现功能
@EnableDiscoveryClient
public class CoisiniSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoisiniSystemApplication.class, args);
    }

    // 分页插件
    @Bean
    public MybatisPlusInterceptor paginationInterceptors(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页拦截器
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setMaxLimit(500L);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return mybatisPlusInterceptor;
    }
}
