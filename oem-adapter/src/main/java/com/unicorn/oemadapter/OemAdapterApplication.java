package com.unicorn.oemadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OemAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OemAdapterApplication.class, args);
	}
}
