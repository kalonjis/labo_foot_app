package com.labospring.LaboFootApp.il.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration class for enabling asynchronous execution support in Spring
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    // You can also customize the thread pool here if needed
}
