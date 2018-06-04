package com.yash.tcvm.exception;

public class ContainerOverFlowException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ContainerOverFlowException(){
		
	}
	
	public ContainerOverFlowException(String errMsg){
		super(errMsg);
	}

}
