package com.chengzhx76.github.test;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/21
 */
public class HystrixTest {

    @Test
    public void testExecute() {
        Object data = new HystrixCommandTestModel("chengzhx76").execute();
        System.out.println(data);
    }

    @Test
    public void testQueue() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Object> data = new HystrixCommandTestModel("chengzhx76").queue();
        System.out.println(data.get(1000L, TimeUnit.MILLISECONDS));
    }

}
