package com.kulhade.app;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Created by vn05f93 on 5/30/17.
 */
@SpringBootApplication
public class KafkaApplication {

    public static void main(String... arg){
        SpringApplication.run(KafkaApplication.class);
    }

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @Bean
    public CommandLineRunner commandLineRunnerProducer(ApplicationContext ctx){
        Logger PR_LOGGER = LoggerFactory.getLogger("producerLogger");
        return args -> {
            for(int i=0;i<100;i++){
                int finalI = i;
                kafkaProducer.send(new ProducerRecord("topic-mirror-maker",i),(recordMetadata, e)->{
                    if(e==null){
                        PR_LOGGER.info(" Produced message "+ finalI +" with offset " +recordMetadata.offset());
                    }else{
                        PR_LOGGER.error(" Unable to send message ",e);
                        kafkaProducer.close();
                    }
                });
            }
        };
    }

    @Bean
    public CommandLineRunner commandLineRunnerConsumer(ApplicationContext ctx){
        Logger CR_LOGGER = LoggerFactory.getLogger("consumerLogger");
        return args -> {
            kafkaConsumer.subscribe(Arrays.asList("topic-mirror-maker"));
            while(true){
                ConsumerRecords<String,String> records = kafkaConsumer.poll(100);
                for(ConsumerRecord record:records){
                    CR_LOGGER.info(" Consumed message "+record.value());
                }
                kafkaConsumer.commitSync();
            }
        };
    }

}
