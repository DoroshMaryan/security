package com.laurentiuspilca.ssia.config;

import com.laurentiuspilca.ssia.postprocessor.MessageBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProjectConfig.class);

    @Bean
    String setString(){
        LOGGER.info("Start ProjectConfig bean");
        return "stringBean";
    }
//    @Bean
//    public MessageBeanPostProcessor messageBeanPostProcessor(){
//        LOGGER.info("Start ProjectConfig bean");
//        return  new MessageBeanPostProcessor();
//    }
}
