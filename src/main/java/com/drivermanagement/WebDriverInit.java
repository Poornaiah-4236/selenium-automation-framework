package com.drivermanagement;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.configuration.ConfigProperties;

public class WebDriverInit {
	private static final Logger log = LogManager.getLogger(WebDriverInit.class);

	private WebDriverInit() {
	}

	public static WebDriver init(String browser) throws MalformedURLException {
		boolean remote = Boolean.parseBoolean(ConfigProperties.getProperty("remote", "false"));
		boolean headless = Boolean.parseBoolean(ConfigProperties.getProperty("headless", "false"));
		log.info("Initializing {} driver (remote={}, headless={})", browser, remote, headless);

		WebDriver driver = remote
				? HubDrivers.createRemoteDriver(browser, new URL(ConfigProperties.getProperty("gridUrl")))
				: createLocalDriver(browser, headless);

		int implicitWaitSeconds = Integer.parseInt(ConfigProperties.getProperty("implicitWaitSeconds", "10"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));

		DriverManagers.setDriver(driver);
		return driver;
	}

	private static WebDriver createLocalDriver(String browser, boolean headless) {
		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			if (headless) {
				chromeOptions.addArguments("--headless=new");
			}
			return new ChromeDriver(chromeOptions);
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (headless) {
				firefoxOptions.addArguments("-headless");
			}
			return new FirefoxDriver(firefoxOptions);
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			if (headless) {
				edgeOptions.addArguments("--headless=new");
			}
			return new EdgeDriver(edgeOptions);
		default:
			throw new IllegalArgumentException("Invalid browser: " + browser);
		}
	}
}
