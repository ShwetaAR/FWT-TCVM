package com.yash.tcvm.exception;

public class FileNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotExistException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
