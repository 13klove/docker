package com.gdev.boot.util.crw;

import org.apache.http.impl.client.CloseableHttpClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@Builder
public class CrwVo {

	private Object input;
	private Object output;
	private CloseableHttpClient client;
	
}
