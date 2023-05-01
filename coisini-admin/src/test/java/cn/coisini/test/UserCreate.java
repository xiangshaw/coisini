package cn.coisini.test;

import cn.coisini.admin.AdminApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

/**
 * @Author: xiaoxiang
 * @Description: 手动创建用户进行登录测试使用
 */
@SpringBootTest(classes = {AdminApplication.class})
public class UserCreate {
    @Test
    public void getSaltPassword() {
        String salt = "123456";
        // ea48576f30be1669971699c09ad05c94
        String pswd = "123456" + salt;
        String saltPswd = DigestUtils.md5DigestAsHex(pswd.getBytes());
        System.out.println(saltPswd);
        /**
         解析：
         salt:123456
         password:ea48576f30be1669971699c09ad05c94
         username:coisini
         INSERT INTO ad_user(NAME,PASSWORD,salt) VALUES("coisini","ea48576f30be1669971699c09ad05c94","123456")
         {"name":"coisini","password":"coisini"}
         */
    }
}
