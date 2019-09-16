package com.es.example.esdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

@Configuration
@EnableSpringDataWebSupport
public class BeanConfig {
    @Bean
    SortHandlerMethodArgumentResolverCustomizer sortCustomizer() {
        return s -> s.setPropertyDelimiter("<-->");
    }
}
