package com.chengzhx76.github.test;

import com.netflix.hystrix.*;
import org.apache.http.client.fluent.Request;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/21
 */
public class HystrixCommandTestModel extends HystrixCommand<Object> {

    private Object data;

    protected HystrixCommandTestModel(Object data) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroupName"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("UserCommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserThreadPoolKey"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutEnabled(true)
                        .withExecutionTimeoutInMilliseconds(3600))); // 执行时间

        this.data = data;
    }

    @Override
    protected String run() throws Exception {

        String response = Request.Get("http://localhost:8081/time-out")
                .connectTimeout(10000)
                .socketTimeout(10000)
                .execute()
                .returnContent()
                .asString();

        System.out.println("--> "+response);

//        return data.toString();
        return response;
    }

    @Override
    protected Object getFallback() {
        System.out.println("---> 进入Fallback");
        return "--Fallback--";
    }

    @Override
    protected String getCacheKey() {
        System.out.println("---> getCacheKey");
        return "_"+data;
    }
}
