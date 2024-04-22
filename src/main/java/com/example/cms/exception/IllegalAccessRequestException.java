package com.example.cms.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IllegalAccessRequestException extends RuntimeException{
	private String message;

	public String getMessage() {
		return message;
	}
}
