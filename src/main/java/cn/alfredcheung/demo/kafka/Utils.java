package cn.alfredcheung.demo.kafka;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zhanghaifeng
 * @time 2018年04月02日 下午4:55
 */
public class Utils {

    public static Properties loadMainProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Consumer.class.getResourceAsStream("/main.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to load main.properties");
        }
        return properties;
    }
}
