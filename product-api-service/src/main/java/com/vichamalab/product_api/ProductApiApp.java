package com.vichamalab.product_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.vichamalab.product_api.storage.StorageProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class ProductApiApp {
	public static void main(String[] args) {
		SpringApplication.run(ProductApiApp.class, args);
	}
}
