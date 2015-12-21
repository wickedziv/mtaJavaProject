package com.danz.javacourse.exception;

import org.algo.exception.PortfolioException;

public class StockNotExistException extends PortfolioException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048265072246347811L;

	public StockNotExistException() {
		// TODO Auto-generated constructor stub
	}

	public StockNotExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public StockNotExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public StockNotExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StockNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
