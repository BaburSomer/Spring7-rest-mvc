package com.babsom.spring7restmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomErrorController {

	@ExceptionHandler//(TransactionSystemException.class)
	ResponseEntity<List<Map<String, String>>> handleJPAViolations(TransactionSystemException ex) {
		ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
		
		if (ex.getCause().getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException violationException = (ConstraintViolationException)ex.getCause().getCause();
			List<Map<String, String>> errors = violationException.getConstraintViolations().stream().map(violation -> {
					Map<String, String> errorMap = new HashMap<>();
					errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
					return errorMap;			
				}).collect(Collectors.toList());
			
			return responseEntity.body(errors);
		}
		
		return responseEntity.build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<List<Map<String, String>>> handleBindErrors(MethodArgumentNotValidException ex) {
		
		List<Map<String, String>> errors = ex.getFieldErrors().stream().map(fieldError -> {
			Map<String, String> errorMap = new HashMap<>();
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			return errorMap;
		}).collect(Collectors.toList());
		
		return ResponseEntity.badRequest().body(errors);
	}
}
