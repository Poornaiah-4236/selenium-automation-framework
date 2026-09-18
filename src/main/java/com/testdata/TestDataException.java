package com.testdata;

public class TestDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TestDataException(String message) {
		super(message);
	}

	public TestDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
