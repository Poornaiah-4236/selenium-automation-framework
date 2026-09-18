package com.drivermanagement;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HubDrivers {
	private static final Logger log = LogManager.getLogger(HubDrivers.class);
	private static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	private HubDrivers() {
	}

	public static WebDriver createRemoteDriver(String browser, URL gridUrl) {
		RemoteWebDriver remoteDriver;
		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--disable-notifications", "--remote-allow-origins=*");
			chromeOptions.setCapability("platformName", "Windows");
			remoteDriver = new RemoteWebDriver(gridUrl, chromeOptions);
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--disable-notifications", "--remote-allow-origins=*");
			edgeOptions.setCapability("platformName", "Windows");
			remoteDriver = new RemoteWebDriver(gridUrl, edgeOptions);
			break;
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--disable-notifications", "--remote-allow-origins=*");
			firefoxOptions.setCapability("platformName", "Windows");
			remoteDriver = new RemoteWebDriver(gridUrl, firefoxOptions);
			break;
		default:
			throw new IllegalArgumentException("Unsupported remote browser: " + browser);
		}
		driver.set(remoteDriver);
		log.info("Created remote WebDriver session on {} for browser {}", gridUrl, browser);
		return remoteDriver;
	}
}
