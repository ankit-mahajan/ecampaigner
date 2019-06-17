package com.ankit.ecampaigner.campaignservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
public class CampaignServiceApplication {

	@Bean
	public Sampler defaultSamplar() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
	@Value("${fanout.exchange}")
	private String fanoutExchange;

	@Value("${campaign.queue}")
	private String queueName;

	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(fanoutExchange);
	}

	@Bean
	Binding binding(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CampaignServiceApplication.class, args);
	}
}
