package com.ali.retail.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoadUtil {
	private static Logger logger = LoggerFactory.getLogger(ConfigLoadUtil.class);
	private static Map<String, Properties> props = new HashMap<>();

	public static String get(String prop, String key) {
		if (!props.containsKey(prop)) {
			loadProp(prop);
		}
		return props.get(prop).getProperty(key);
	}

	public static String get(String key) {
		return get("env", key);
	}

	public static Properties getProterties(String prop) {
		if (!props.containsKey(prop)) {
			loadProp(prop);
		}
		return props.get(prop);
	}

	private static void loadProp(String prop) {
		if (!props.containsKey(prop)) {
			synchronized (props) {
				if (!props.containsKey(prop)) {
					try {
						Properties p = new Properties();
						InputStream in;

						File file = new File(System.getProperty("user.dir") + "/config/" + prop + ".properties");

						if (file.exists())
							in = new FileInputStream(file);
						else
							in = ConfigLoadUtil.class.getResourceAsStream("/" + prop + ".properties");
						p.load(in);
						props.put(prop, p);
					} catch (FileNotFoundException e) {
						logger.error("properties file {} not exist !", "/config/" + prop + ".properties", e);
					} catch (IOException e) {
						logger.error("load properties {} failed ! ", prop + ".properties", e);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
	}

}
