package com.gdev.boot.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdev.boot.domain.exception.ValidExceptionVo;

@ControllerAdvice
public class ExceptionController {

	@ResponseBody
	@ExceptionHandler(RuntimeValidException.class)
	public ValidExceptionVo validException(RuntimeValidException rse) {
		return ValidExceptionVo.builder().httpStatus(HttpStatus.OK).msg(rse.getMsg()).build();
	}
	
}
