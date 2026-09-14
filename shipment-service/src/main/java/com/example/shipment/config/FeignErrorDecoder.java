package com.example.shipment.config;

import com.example.shipment.exception.UserNotFoundException;
import com.example.shipment.exception.UserServiceUnavailableException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {

		if (response.status() == 404) {
			return new UserNotFoundException("User not found");
		}

		if (response.status() >= 500) {
			return new UserServiceUnavailableException("User Service is unavailable");
		}

		return new RuntimeException("Feign call failed with status: " + response.status());
	}
}