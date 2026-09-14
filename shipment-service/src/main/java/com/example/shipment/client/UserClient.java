package com.example.shipment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shipment.config.FeignClientConfig;
import com.example.shipment.config.FeignErrorDecoder;
import com.example.shipment.dto.UserResponse;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {

	@GetMapping("/api/users/{id}")
	UserResponse getUserById(@PathVariable("id") Long userId);
}
