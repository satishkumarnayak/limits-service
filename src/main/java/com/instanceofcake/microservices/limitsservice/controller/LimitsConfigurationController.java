package com.instanceofcake.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instanceofcake.microservices.limitsservice.bean.LimitConfiguration;
import com.instanceofcake.microservices.limitsservice.config.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {

	/*
	 * @Autowired Environment environment;
	 */

	@Autowired
	Configuration config;

	@GetMapping("/limits")
	public LimitConfiguration retriveLimitsFromConfigurations() {
		return new LimitConfiguration(config.getMax(), config.getMin());
		// return new
		// LimitConfiguration(Integer.parseInt(environment.getProperty("limits-service.max")),
		// Integer.parseInt(environment.getProperty("limits-service.min")));
	}

	@GetMapping("/limits-fault-tolerance")
	@HystrixCommand(fallbackMethod="fallbackRetriveLimitsFromConfigurations")
	public LimitConfiguration retriveLimitsFromConfigurationsFaultTolerance() {
		throw new RuntimeException();
		
	}

	public LimitConfiguration fallbackRetriveLimitsFromConfigurations() {
		return new LimitConfiguration(999, 9);
	}
}
