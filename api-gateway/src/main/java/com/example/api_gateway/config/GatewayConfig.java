package com.example.api_gateway.config;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

	@Bean
	public RouterFunction<ServerResponse> userRoute() {

		return route("user-service").route(path("/api/users/**"), http()).filter(lb("user-service")).build();
	}

	@Bean
	public RouterFunction<ServerResponse> shipmentRoute() {

		return route("shipment-service").route(path("/api/shipments/**"), http()).filter(lb("shipment-service"))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> trackingRoute() {

		return route("tracking-service").route(path("/api/tracking/**"), http()).filter(lb("tracking-service")).build();
	}

	@Bean
	public RouterFunction<ServerResponse> authRoute() {

		return route("auth-service").route(path("/auth/**"), http()).filter(lb("auth-service")).build();
	}
}