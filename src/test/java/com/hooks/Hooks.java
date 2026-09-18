package com.hooks;

import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.configuration.ConfigProperties;
import com.drivermanagement.DriverManagers;
import com.drivermanagement.WebDriverInit;
import com.listeners.ExtentReport;
import com.utils.ScreenshotUtil;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	private static final Logger log = LogManager.getLogger(Hooks.class);

	@Before
	public void setUp(Scenario scenario) throws MalformedURLException {
		String browser = System.getProperty("browser", ConfigProperties.getProperty("browser", "chrome"));
		WebDriverInit.init(browser);
		DriverManagers.getDriver().get(ConfigProperties.getProperty("url"));
		ExtentReport.setTest(ExtentReport.extent.createTest(scenario.getName()));
	}

	@After
	public void tearDown(Scenario scenario) {
		WebDriver driver = DriverManagers.getDriver();
		if (driver == null) {
			log.warn("Driver is null in @After hook for scenario: {}", scenario.getName());
			return;
		}
		if (scenario.isFailed()) {
			handleFailure(driver, scenario);
		} else {
			ExtentReport.getTest().pass("Scenario passed: " + scenario.getName());
		}
		DriverManagers.unload();
	}

	private void handleFailure(WebDriver driver, Scenario scenario) {
		byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot, "image/png", scenario.getName());

		ExtentReport.getTest().fail("Scenario failed: " + scenario.getName());
		String path = ScreenshotUtil.captureScreenshot(driver, scenario.getName().replaceAll("\\s+", "_"));
		if (path != null) {
			ExtentReport.getTest().addScreenCaptureFromPath(path);
		}
		log.warn("Screenshot captured for failed scenario: {}", scenario.getName());
	}
}
