package com.gdev.boot.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidExceptionVo {

	private HttpStatus httpStatus;
	
	private String msg;
	
}
