package com.gdev.boot.domain.sms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@Builder
public class SmsVo {

	private String title;
	private String context;
	private List<String> receiver;
	
}
