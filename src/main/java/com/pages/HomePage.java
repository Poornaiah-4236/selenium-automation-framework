package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.drivermanagement.WaitUtils;

public class HomePage {
	private final WebDriver driver;
	private final By productsHeader = By.cssSelector("[data-test='title']");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public String getProductsHeaderText() {
		return WaitUtils.waitForVisible(driver, productsHeader).getText();
	}
}
