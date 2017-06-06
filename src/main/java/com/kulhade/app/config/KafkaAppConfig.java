package com.kulhade.app.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vn05f93 on 5/26/17.
 */
@Configuration
public class KafkaAppConfig {

    @Value("config/producer-config.properties")
    Resource producerConfig;

    @Value("config/consumer-config.properties")
    Resource consumerConfig;

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public KafkaProducer<String,String> kafkaProducer() throws IOException {
        InputStream inputStream = producerConfig.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        return new KafkaProducer(properties);
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public KafkaConsumer<String,String> kafkaConsumer() throws IOException{
        InputStream inputStream = consumerConfig.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        return new KafkaConsumer(properties);
    }
}
