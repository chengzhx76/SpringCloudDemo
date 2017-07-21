package com.chengzhx76.github.test;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.apache.http.client.fluent.Request;
import rx.Observable;
import rx.Subscriber;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/21
 */
public class HystrixObservableCommandTestModel extends HystrixObservableCommand<Object> {

    private Object data;

    public HystrixObservableCommandTestModel(Object data) {
        super(HystrixCommandGroupKey.Factory.asKey("UserObservable"));
        this.data = data;
    }

    @Override
    protected Observable<Object> construct() {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        String response = Request.Get("http://localhost:8081/user/"+data)
                                .connectTimeout(1000)
                                .socketTimeout(1000)
                                .execute()
                                .returnContent()
                                .asString();
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
