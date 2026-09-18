package com.drivermanagement;

import org.openqa.selenium.WebDriver;

public class DriverManagers {
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver getDriver() {

		return driver.get();
	}

	public static void setDriver(WebDriver driverInstance) {

		driver.set(driverInstance);

	}

	public static void unload() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

}
