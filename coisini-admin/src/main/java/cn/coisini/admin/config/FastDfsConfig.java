package cn.coisini.admin.config;

import cn.coisini.common.fastdfs.FastDFSClient;
import cn.coisini.common.fastdfs.FdfsConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xiaoxiang
 * @Description: 引用fastdfs
 */
@Configuration
//@ComponentScan(value = "cn.coisini.common.fastdfs")
@ComponentScan(basePackageClasses  = {FastDFSClient.class, FdfsConfiguration.class})
public class FastDfsConfig {
}
