package com.sjtu.zc.trader.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MvcConfiguration {

	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate restTemplate= new RestTemplate();
		return restTemplate;
	}

	@Bean
	public HttpHeaders getMultiValueMap(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","application/json;charset=UTF-8");
		return headers;
	}
}
