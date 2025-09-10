package com.example.eventcollector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling // This annotation enables the @Scheduled feature for our ETL job
public class SchedulingConfig {
}