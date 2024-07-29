package cn.coisini.wemidia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: xiaoxiang
 * @Description: 自媒体微服务启动类
 */
@SpringBootApplication
//@MapperScan("cn.coisini.wemedia.mapper")/*扫描mapper接口*/
public class WemediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class,args);
    }
}
