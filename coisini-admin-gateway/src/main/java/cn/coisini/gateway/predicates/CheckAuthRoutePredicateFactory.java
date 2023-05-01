package cn.coisini.gateway.predicates;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author: xiaoxiang
 * @Description: 自定义路由断言工产
 *
 * 1. 必须Spring组件bean
 * 2. 类必须加上RoutePredicateFactory作为结尾
 * 3. 必须继承抽象断言工产 AbstractRoutePredicateFactory
 * 4. 必须声明静态内部类声明属性来接收配置文件中对应的断言的信息
 * 5. 需要结合shortcutFieldOrder进行绑定
 * 6. 通过apply进行逻辑判断true就是匹配成功false匹配失败
 */
@Component
@Log4j2
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {
    public CheckAuthRoutePredicateFactory() {
        super(CheckAuthRoutePredicateFactory.Config.class);
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name");
    }
    @Override
    public Predicate<ServerWebExchange> apply(CheckAuthRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                if(config.getName().equals("coisini")){
                    return true;
                }
                return false;
            }

        };
    }

    // 用于接收配置文件中 断言的信息
    @Validated
    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
