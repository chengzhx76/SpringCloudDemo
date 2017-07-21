package com.chengzhx76.github.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.client.fluent.Request;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/21
 */
public class HystrixCommandTestModel extends HystrixCommand<Object> {

    private Object data;

    protected HystrixCommandTestModel(Object data) {
        super(HystrixCommandGroupKey.Factory.asKey("User"));

        this.data = data;
    }

    @Override
    protected String run() throws Exception {

        String response = Request.Get("http://localhost:8081/user/2")
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute()
                .returnContent()
                .asString();

//        System.out.println("--> "+response);

//        return data.toString();
        return response;
    }
}
