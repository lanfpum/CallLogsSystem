package lxpsee.top;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 14:43.
 * <p>
 * 加载外部配置文件，根据配置key获取值得方法
 */
public class PropertiesUtil {
    public static Properties properties;

    static {
        try {
            InputStream kafkaInput = ClassLoader.getSystemResourceAsStream("kafka.properties");
            properties = new Properties();
            properties.load(kafkaInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPro(String key) {
        return properties.getProperty(key);
    }

}
