package com.chengzhx76.gethub.client;

import com.chengzhx76.github.service.HelloService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
@FeignClient("eureka-client")
public interface RefactorHelloService extends HelloService {

}
