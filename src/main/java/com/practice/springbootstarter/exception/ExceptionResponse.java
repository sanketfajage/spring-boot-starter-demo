package com.practice.springbootstarter.exception;

import java.util.Date;

public class ExceptionResponse {

	private Date timestamp;
	private String errorMessage;
	private String errorDetails;

	public ExceptionResponse(Date timestamp, String errorMessage, String errorDetails) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
		this.errorDetails = errorDetails;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorDetails() {
		return errorDetails;
	}
}
