package cn.alfredcheung.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

/**
 * @author zhanghaifeng
 * @time 2018年04月02日 下午2:51
 */
public class Consumer {

    private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

    private static KafkaConsumer<Long, String> createConsumer() {
        Properties properties = new Properties();
        try {
            properties.load(Consumer.class.getResourceAsStream("/consumer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to load consumer.properties for kafka");
        }
        return new KafkaConsumer<Long, String>(properties);
    }

    public static void main(String[] args) throws InterruptedException {

        // create consumer
        KafkaConsumer<Long, String> consumer = createConsumer();

        // subscribe topics
        Properties properties = Utils.loadMainProperties();
        consumer.subscribe(Collections.singletonList(properties.getProperty("topic")));

        // main loop
        while (!Thread.interrupted()) {

            // poll data
            ConsumerRecords<Long, String> records = consumer.poll(10);
            if (records != null && records.count() > 0) {
                for (ConsumerRecord<Long, String> record : records) {
                    logger.error("consume_ok,record={}", record);
                }
            } else {
                Thread.sleep(1000);
            }
        }
        consumer.close();
    }
}
