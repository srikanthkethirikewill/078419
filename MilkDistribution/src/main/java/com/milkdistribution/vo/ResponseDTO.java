package com.milkdistribution.vo;

public class ResponseDTO<T> {
	
	private Result result;
	
	private T body;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
	
}
