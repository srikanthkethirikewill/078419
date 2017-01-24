package com.milkdistribution.vo;

public class RequestDTO<T> {
	
	private Header header;
	
	private T body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
	
}
