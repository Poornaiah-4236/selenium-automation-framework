package com.drivermanagement;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.configuration.ConfigProperties;

public class WaitUtils {

	private WaitUtils() {
	}

	private static WebDriverWait wait(WebDriver driver) {
		int seconds = Integer.parseInt(ConfigProperties.getProperty("explicitWaitSeconds", "20"));
		return new WebDriverWait(driver, Duration.ofSeconds(seconds));
	}

	public static WebElement waitForVisible(WebDriver driver, By locator) {
		return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static WebElement waitForClickable(WebDriver driver, By locator) {
		return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static boolean waitForTextPresent(WebDriver driver, By locator, String text) {
		return wait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}
}
