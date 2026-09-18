package com.listeners;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.drivermanagement.DriverManagers;
import com.utils.ScreenshotUtil;

import io.cucumber.testng.AbstractTestNGCucumberTests;

public class ExtentReport implements ITestListener {
	private static final Logger log = LogManager.getLogger(ExtentReport.class);
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		extent = getInstance();
	}

	public static void setTest(ExtentTest testlog) {
		test.set(testlog);
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static ExtentReports getInstance() {
		if (extent == null) {
			createInstance();
		}
		return extent;
	}

	public static void createInstance() {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator
				+ "TestResult" + File.separator + "extent-report-" + timestamp + ".html");
		spark.config().setReportName("Selenium Automation Report");
		spark.config().setDocumentTitle("Test Execution Report");
		extent.attachReporter(spark);
		extent.setSystemInfo("Host Name", "Localhost");
		log.info("Extent report initialized");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	/**
	 * Cucumber scenarios get their own ExtentTest (named after the scenario) created in the
	 * Cucumber {@code @Before} hook, so this listener only manages reporting for plain TestNG
	 * tests to avoid creating a second, orphaned report node per scenario.
	 */
	private boolean isCucumberScenario(ITestResult result) {
		return result.getInstance() instanceof AbstractTestNGCucumberTests;
	}

	@Override
	public void onTestStart(ITestResult result) {
		if (!isCucumberScenario(result)) {
			test.set(extent.createTest(result.getMethod().getMethodName()));
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if (!isCucumberScenario(result)) {
			test.get().pass("Test passed");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		if (isCucumberScenario(result)) {
			return;
		}
		test.get().fail(result.getThrowable());
		if (DriverManagers.getDriver() != null) {
			String path = ScreenshotUtil.captureScreenshot(DriverManagers.getDriver(), result.getMethod().getMethodName());
			if (path != null) {
				test.get().addScreenCaptureFromPath(path);
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		if (!isCucumberScenario(result)) {
			test.get().skip(result.getThrowable());
		}
	}

}
