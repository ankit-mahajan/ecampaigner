package com.ankit.ecampaigner.emailservice.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmailConsumer {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EmailExecutor emailExecutor;
	
	@RabbitListener(queues="${campaign.queue}")
	public void receiveMessage(String message) {
		logger.info("Entering receiveMessage {}", message);
		
		try {
			Campaign campaign = new ObjectMapper().readValue(message, Campaign.class);
			emailExecutor.processMessage(campaign);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		logger.info("Exiting receiveMessage");
	}
}
