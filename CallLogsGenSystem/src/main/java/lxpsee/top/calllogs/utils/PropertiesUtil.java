package lxpsee.top.calllogs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/27 17:38.
 * <p>
 * 获取配置属性文件的工具类
 */
public class PropertiesUtil {
    private static Properties properties;

    static {
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("genData.conf");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPro(String key) {
        return properties.getProperty(key);
    }

    public static String getStrPro(String key) {
        return properties.getProperty(key);
    }

    public static int getIntPro(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
