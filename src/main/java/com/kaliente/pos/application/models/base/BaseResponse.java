package com.kaliente.pos.application.models.base;


public class BaseResponse<T> {
	private String message;
	private T payload;
	
	public BaseResponse(T payload) {
		super();
		this.payload = payload;
	}
	
	public BaseResponse(T payload, String message) {
		super();
		this.payload = payload;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
	
}
