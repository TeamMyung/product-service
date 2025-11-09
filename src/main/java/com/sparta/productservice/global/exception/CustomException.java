package com.sparta.productservice.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getDetails());
		this.errorCode = errorCode;
	}
}
