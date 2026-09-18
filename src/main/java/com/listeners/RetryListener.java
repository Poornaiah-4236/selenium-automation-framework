package com.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {
	private static final Logger log = LogManager.getLogger(RetryListener.class);
	private int attempt = 0;
	private final int maxRetries = 3;

	@Override
	public boolean retry(ITestResult result) {
		if (attempt < maxRetries) {
			attempt++;
			log.warn("Retrying test case {} (attempt {} of {})", result.getName(), attempt, maxRetries);
			return true;
		}
		return false;
	}
}
