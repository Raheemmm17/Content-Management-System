package com.example.cms.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.EmailAlreadyPresentException;
import com.example.cms.exception.TitleIsAlreadyPresentException;
import com.example.cms.exception.TitleNotAvailableException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	private ErrorStructure<String> structure;
	
	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status, String message, String rootCause){
		return new ResponseEntity<ErrorStructure<String>>(structure.setStatusCode(status.value())
				.setErrorMessage(message)
				.setRootCause(rootCause),status);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(EmailAlreadyPresentException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),"User already exists with the given gmail");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserNotFoundByIdException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),"User is not found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UsernameNotFoundException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),"User is found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(TitleIsAlreadyPresentException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),"Title is already available");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(TitleNotAvailableException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),"Title is not found ");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(TopicNotSpecifiedException ex){
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(),"Topic is not found");
	}
}


