package com.example.cms.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TopicNotSpecifiedException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}
}
