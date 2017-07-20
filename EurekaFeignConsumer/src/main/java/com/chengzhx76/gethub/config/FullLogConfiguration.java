package com.chengzhx76.gethub.config;

import feign.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
@Configurable
public class FullLogConfiguration {

    /**
     * 配合使用
     * @FeignClient(name = "eureka-client", configuration = FullLogConfiguration.class)
     */

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
