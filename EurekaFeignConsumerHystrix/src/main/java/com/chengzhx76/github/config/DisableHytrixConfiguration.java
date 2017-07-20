package com.chengzhx76.github.config;

import feign.Feign;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
@Configurable
public class DisableHytrixConfiguration {

    /**
     * @FeignClient(name = "eureka-client", configuration = DisableHytrixConfiguration.class)
     */

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}
