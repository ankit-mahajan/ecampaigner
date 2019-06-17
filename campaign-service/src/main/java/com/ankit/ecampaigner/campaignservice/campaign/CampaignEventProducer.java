package com.ankit.ecampaigner.campaignservice.campaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CampaignEventProducer {

protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Value("${campaign.queue}")
	private String queueName;
    
    public void produce(Campaign campaign) throws Exception {
		logger.info("Storing notification...");

		ObjectMapper objectMapper = new ObjectMapper();
		String message = objectMapper.writeValueAsString(campaign);
		rabbitTemplate.convertAndSend(queueName, message);

		logger.info("Notification stored in queue sucessfully");
	}
    
}
