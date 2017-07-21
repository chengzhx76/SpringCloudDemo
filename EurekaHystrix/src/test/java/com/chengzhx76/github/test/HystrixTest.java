package com.chengzhx76.github.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Desc:
 * HystrixObservableCommand vs HystrixCommand：
 * 1）前者的命令封装在contruct()，后者在run()；前者的fallback处理封装在resumeWithFallback()，后者在getFallBack()
 * 2）前者用主线程执行contruct()，后者另起线程来执行run()
 * 3）前者可以在contruct()中顺序定义多个onNext，当调用subscribe()注册成功后将依次执行这些onNext，后者只能在run()中返回一个值（即一个onNext）
 *
 * HystrixObservableCommand的observe()与toObservable()的区别：
 * 1）observe()会立即执行HelloWorldHystrixObservableCommand.construct()；toObservable()要在toBlocking().single()或subscribe()时才执行HelloWorldHystrixObservableCommand.construct()
 * 2）observe()中，toBlocking().single()和subscribe()可以共存；在toObservable()中不行，因为两者都会触发执行HelloWorldHystrixObservableCommand.construct()，这违反了同一个HelloWorldHystrixObservableCommand对象只能执行construct()一次原则
 * @throws Exception
 *
 * Author: chengzhx76@qq.com
 * Date: 2017/7/21
 */
public class HystrixTest {

    private final Logger _log = LoggerFactory.getLogger(HystrixTest.class);

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

    @Test
    public void testObservable() {
        Observable<Object> hotObservable = new HystrixObservableCommandTestModel(1).observe();
        Observable<Object> coldObservable = new HystrixObservableCommandTestModel(1).toObservable();

        hotObservable.subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                // onNext/onError完成之后最后回调
                System.out.println("---> execute onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                // 当产生异常时回调
                System.out.println("---> onError" + e.getMessage());
            }
            @Override
            public void onNext(Object data) {
                // 获取结果后回调
                System.out.println("---> onNext " + data);
            }
        });
    }

}
