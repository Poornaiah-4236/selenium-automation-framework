package com.runner;

import java.net.MalformedURLException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.configuration.ConfigProperties;
import com.listeners.ExtentReport;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = { "com.stepdefinitions", "com.hooks" },
		plugin = { "pretty", "html:target/cucumber-reports.html" },
		dryRun = false,
		// Overridable at runtime with -Dcucumber.filter.tags=@SomeTag
		tags = "@Smoke")
@Listeners(ExtentReport.class)
public class TestRunner extends AbstractTestNGCucumberTests {

	@DataProvider(parallel = true)
	@Override
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeClass
	@Parameters("browser")
	public void setBrowser(@Optional("") String browser) throws MalformedURLException {
		String resolved = browser.isBlank() ? ConfigProperties.getProperty("browser", "chrome") : browser;
		System.setProperty("browser", resolved);
		ConfigProperties.prop.setProperty("browser", resolved);
	}

}
