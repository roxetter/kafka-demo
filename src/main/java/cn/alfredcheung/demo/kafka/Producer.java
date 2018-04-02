package cn.alfredcheung.demo.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author zhanghaifeng
 * @time 2018年04月02日 下午4:46
 */
public class Producer {

    private final static Logger logger = LoggerFactory.getLogger(Producer.class);

    private static KafkaProducer<Long, String> createProducer() {

        Properties properties = new Properties();
        try {
            properties.load(Consumer.class.getResourceAsStream("/producer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to load producer.properties for kafka");
        }
        return new KafkaProducer<Long, String>(properties);

    }

    public static void main(String[] args) {

        Properties properties = Utils.loadMainProperties();

        // create producer
        KafkaProducer<Long, String> producer = createProducer();

        Scanner scanner = new Scanner(System.in);

        // main loop
        while (!Thread.interrupted()) {

            // read input string
            String string = scanner.nextLine();

            // and write into kafka
            // use callback to check result
            producer.send(new ProducerRecord<Long, String>(properties.getProperty("topic"), string), new Callback() {
                public void onCompletion(RecordMetadata recordMetadata,
                                         Exception e) {

                    if (recordMetadata != null) {
                        logger.info("kafka_produce_ok,partition={},offset={}", recordMetadata.partition(), recordMetadata.offset());
                    } else {
                        logger.error("kafka_produce_fail", e);
                    }
                }
            });
        }
        producer.close();
    }
}
