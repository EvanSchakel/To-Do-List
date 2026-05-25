package com.example.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class EtagConfig {

    // ⚡ Bolt Performance Optimization:
    // Adding ShallowEtagHeaderFilter automatically generates ETags based on response content.
    // This allows clients to use If-None-Match headers. If the data hasn't changed,
    // the server returns a 304 Not Modified status without a response body, saving
    // bandwidth and client-side processing time.
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
