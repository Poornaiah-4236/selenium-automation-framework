package com.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigProperties {
	private static final Logger log = LogManager.getLogger(ConfigProperties.class);
	private static final Path FILE_PATH = Path.of(System.getProperty("user.dir"), "Config.properties");

	public static final Properties prop = load();

	private ConfigProperties() {
	}

	private static Properties load() {
		if (!Files.exists(FILE_PATH)) {
			throw new ConfigurationException("Config.properties not found at: " + FILE_PATH.toAbsolutePath());
		}
		Properties properties = new Properties();
		try (InputStream is = Files.newInputStream(FILE_PATH)) {
			properties.load(is);
		} catch (IOException e) {
			throw new ConfigurationException("Could not load Config.properties at: " + FILE_PATH.toAbsolutePath(), e);
		}
		log.info("Loaded configuration from {}", FILE_PATH.toAbsolutePath());
		return properties;
	}

	public static String getProperty(String key) {
		return getProperty(key, null);
	}

	public static String getProperty(String key, String defaultValue) {
		String systemValue = System.getProperty(key);
		if (systemValue != null && !systemValue.isEmpty()) {
			return systemValue;
		}
		return prop.getProperty(key, defaultValue);
	}
}
