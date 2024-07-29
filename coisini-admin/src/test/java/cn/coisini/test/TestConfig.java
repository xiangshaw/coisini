package cn.coisini.test;

import cn.coisini.common.fastdfs.FastDFSClient;
import cn.coisini.common.fastdfs.FdfsConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

/**
 * @Author: xiaoxiang
 * @Description: 测试扫描组件是否正常
 */

@Configuration
//@ComponentScan(value = "cn.coisini.common.fastdfs")
@ComponentScan(basePackageClasses  = {FastDFSClient.class, FdfsConfiguration.class})
public class TestConfig {
    @Test
    public void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
    }
}
