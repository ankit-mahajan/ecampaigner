package com.ankit.ecampaigner.emailservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EmailServiceApplication {

	@Bean
	public Sampler defaultSamplar() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
	@Bean
	public ExecutorService executorService() {
		ExecutorService executorService = new ThreadPoolExecutor(1, 2, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		return executorService;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}
}
