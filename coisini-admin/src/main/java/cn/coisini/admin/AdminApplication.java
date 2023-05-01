package cn.coisini.admin;

import cn.coisini.common.utils.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @Author: xiaoxiang
 * @Description:
 */
@SpringBootApplication
// 服务注册发现
@EnableDiscoveryClient
// 开启 OpenFeign 功能
@EnableFeignClients
// 扫描mapper接口
@MapperScan("cn.coisini.admin.mapper")
public class AdminApplication {
    @Value("${workId}")
    private int workId;
    @Value("${datacenterId}")
    private int datacenterId;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AdminApplication.class, args);
       /* while (true) {
            String name = applicationContext.getEnvironment().getProperty("config.name");
            String post = applicationContext.getEnvironment().getProperty("server.port");
            System.out.println("name :"+name);
            System.out.println("post :"+post);
            // 每一秒加载一次，查询注册中心配置是否变更
            TimeUnit.SECONDS.sleep(2);
        }*/
    }

    // 分布式ID
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
    // 远程调用其他服务
   /* @Bean
    @LoadBalanced // 添加nacos后，添加负载均衡器
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        return restTemplate;
    }*/
}
