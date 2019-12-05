package com.hanyun.platform.settle.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取properties文件
 * PropertiesUtil
 */
public class PropertiesUtil {

    public static Properties getProperties(String properiesName) throws Exception{
        Properties properties;
        try {
            properties = new Properties();
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName));
        } catch (IOException e) {
            throw new RuntimeException("读取properties文件失败!", e);
        }
        return properties;
    }
}
