package com.sdyc.sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

public class Config {

  private static Log log = LogFactory.getLog(Config.class);

  public static Properties properties = new Properties();

  static {
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
      log.info("load config :" + properties);
    } catch (IOException e) {
      log.error(e);
      log.error("cls.properties 加载失败");
    }
  }

  public static String get(String k) {
    return properties.getProperty(k);
  }

  public static String get(String k,String defaultValue) {
	    return properties.getProperty(k,defaultValue);
	  }
}
