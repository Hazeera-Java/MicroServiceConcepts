package com.example.shipment.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {

			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();

			if (attributes != null) {

				HttpServletRequest request = attributes.getRequest();

				String authorization = request.getHeader("Authorization");

				System.out.println("========== FEIGN JWT CHECK ==========");
				System.out.println("Authorization Header: " + authorization);
				System.out.println("=====================================");

				if (authorization != null && !authorization.isBlank()) {
					requestTemplate.header("Authorization", authorization);
				}
			}
		};
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}
}