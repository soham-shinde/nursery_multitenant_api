package com.api.nursery_system.multitenancy.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;


@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

   

     @Bean
    public Logger appLogger() {
        return (Logger) LoggerFactory.getLogger("ApplicationLogger");
    }
}
