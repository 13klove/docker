package com.gdev.boot.config.exception;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
public class RuntimeValidException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String msg;

	public static void valid(BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ObjectError error = CollectionUtils.firstElement(bindingResult.getAllErrors());
			throw new RuntimeValidException(error.getDefaultMessage());
		}
	}
	
}
