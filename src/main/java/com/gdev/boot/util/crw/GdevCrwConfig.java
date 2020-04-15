package com.gdev.boot.util.crw;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GdevCrwConfig {

	@Bean(name="httpClient")
	public CloseableHttpClient getHttpClient(){
		return HttpClientFactory.httpClient(50, 3000);
	}	
	
}
