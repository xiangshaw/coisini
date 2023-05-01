package cn.coisini.test;

import cn.coisini.common.utils.IdWorker;

/**
 * @Author: xiaoxiang
 * @Description: 分布式ID测试
 */
public class IdTest {
    public static void main(String[] args) {
        IdWorker idWorker = new IdWorker(1, 1);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }
}
