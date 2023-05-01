package cn.coisini.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: xiaoxiang
 * @Description: admin网关引导类
 */

@SpringBootApplication // 标识 Spring Boot项目
@EnableDiscoveryClient // 服务注册到Nacos中
public class AdminGatewayApplication {
    public static void main(String[] args){
        SpringApplication.run(AdminGatewayApplication.class,args);
    }

}
