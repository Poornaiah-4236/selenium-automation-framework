package com.tests;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.configuration.ConfigProperties;
import com.drivermanagement.DriverManagers;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.testdata.ExcelDataReader;

public class LoginTest extends BaseTest {
	private static final Logger log = LogManager.getLogger(LoginTest.class);
	private static final String TEST_DATA_FILE = System.getProperty("user.dir") + File.separator
			+ "src/test/resources/TestData/SampleTestData.xlsx";

	@Test
	public void loginTest() {
		LoginPage login = new LoginPage(DriverManagers.getDriver());
		login.login(ConfigProperties.getProperty("username"), ConfigProperties.getProperty("password"));

		HomePage home = new HomePage(DriverManagers.getDriver());
		Assert.assertEquals(home.getProductsHeaderText(), "Products");
		log.info("Login functionality validated successfully");
	}

	@Test
	public void getLoginData() {
		List<String> usernames = ExcelDataReader.getColumnValues(TEST_DATA_FILE, "Sheet1", "Username");
		log.info("Usernames from Excel: {}", usernames);
	}
}
