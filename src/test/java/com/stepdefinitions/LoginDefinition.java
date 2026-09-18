package com.stepdefinitions;

import org.testng.Assert;

import com.drivermanagement.DriverManagers;
import com.pages.HomePage;
import com.pages.LoginPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginDefinition {

	@When("user enters {string} and {string}")
	public void user_is_on_login_page(String username, String password) {
		LoginPage login = new LoginPage(DriverManagers.getDriver());
		login.login(username, password);
	}

	@Then("user should see {string}")
	public void user_should_see(String expectedResult) {
		if ("Products".equals(expectedResult)) {
			HomePage home = new HomePage(DriverManagers.getDriver());
			Assert.assertEquals(home.getProductsHeaderText(), expectedResult);
		} else {
			LoginPage login = new LoginPage(DriverManagers.getDriver());
			Assert.assertEquals(login.getErrorMessage(), expectedResult);
		}
	}

}
