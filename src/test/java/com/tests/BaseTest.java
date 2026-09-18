package com.tests;

import java.net.MalformedURLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.configuration.ConfigProperties;
import com.drivermanagement.DriverManagers;
import com.drivermanagement.WebDriverInit;

public class BaseTest {

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws MalformedURLException {
		String browser = System.getProperty("browser", ConfigProperties.getProperty("browser", "chrome"));
		WebDriverInit.init(browser);
		DriverManagers.getDriver().get(ConfigProperties.getProperty("url"));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverManagers.unload();
	}
}
