package com.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
	private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);

	private ScreenshotUtil() {
	}

	public static String captureScreenshot(WebDriver driver, String testName) {
		Path dir = Path.of(System.getProperty("user.dir"), "screenshots");
		Path destination = dir.resolve(testName + ".png");
		try {
			Files.createDirectories(dir);
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, destination.toFile());
			return destination.toString();
		} catch (IOException e) {
			log.warn("Could not capture screenshot for {}: {}", testName, e.getMessage());
			return null;
		}
	}
}
