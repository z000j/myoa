package com.web.oa.exception;

/**
 * @description: TODO
 * @author 黄培金
 * @date 2020/8/25 19:51
 * @version 1.0
 */
public class CustomException extends Exception {
	
	//异常信息
	private String message;
	
	public CustomException(String message){
		super(message);
		this.message = message;
		
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
