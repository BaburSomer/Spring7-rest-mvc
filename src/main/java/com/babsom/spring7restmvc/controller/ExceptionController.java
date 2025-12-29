package com.babsom.spring7restmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionController {
//	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<HttpStatus> handleNotFoundException () {
		System.out.println("I am in handleNotFoundException () in ExceptionController");
		return ResponseEntity.notFound().build();
	}
}
