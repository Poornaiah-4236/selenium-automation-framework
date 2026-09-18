package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.drivermanagement.WaitUtils;

public class LoginPage {
	private final WebDriver driver;
	private final By userName = By.id("user-name");
	private final By password = By.xpath("//input[@id='password']");
	private final By loginButton = By.xpath("//input[@id='login-button']");
	private final By errorMessage = By.cssSelector("[data-test='error']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void login(String username, String userPassword) {
		WaitUtils.waitForVisible(driver, userName).sendKeys(username);
		driver.findElement(password).sendKeys(userPassword);
		WaitUtils.waitForClickable(driver, loginButton).click();
	}

	public String getErrorMessage() {
		return WaitUtils.waitForVisible(driver, errorMessage).getText();
	}
}
