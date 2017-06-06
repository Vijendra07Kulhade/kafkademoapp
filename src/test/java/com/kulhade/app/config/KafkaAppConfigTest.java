package com.kulhade.app.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by vn05f93 on 6/5/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KafkaAppConfig.class})
public class KafkaAppConfigTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @Test
    public void testKafkaProducerInIt()throws Exception{
        Assert.assertNotNull(kafkaProducer);
    }

    @Test
    public void testKafkaConsumerInIt()throws Exception{
        Assert.assertNotNull(kafkaConsumer);
    }
}
