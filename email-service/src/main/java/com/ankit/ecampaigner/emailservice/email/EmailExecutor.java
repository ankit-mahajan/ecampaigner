package com.ankit.ecampaigner.emailservice.email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailExecutor {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExecutorService executorService;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CampaignServiceProxy campaignProxy;
	
	public void processMessage(Campaign campaign) {
		logger.info("Entering processMessage {}", campaign);

		final List<Future<EmailStatus>> futures = new ArrayList<>();
		final List<String> emailList = Arrays.asList(campaign.getEmailTo().split(","));

		for (String emailAddress : emailList) {
			Email email = new Email(campaign.getId(), EmailStatus.IN_PROGRESS, emailAddress);
			Future<EmailStatus> future = executorService
					.submit(new EmailWorker(email, campaign, emailService, emailRepository));
			futures.add(future);
		}

		new Thread(() -> updateEmailCampaign(campaign, futures)).start();

		logger.info("Exiting processMessage");
	}
	
	private void updateEmailCampaign(Campaign campaign, List<Future<EmailStatus>> futures) {
		logger.info("Entering updateEmailCampaign. Campaign: {}, Futures: {}", campaign, futures);

		int success = 0;
		int failures = 0;
		
		Status statusCode = Status.IN_PROGRESS;
		for (Future<EmailStatus> future : futures) {
			try {
				EmailStatus status = future.get();
				if (EmailStatus.SUCCESS.equals(status)) {
					success++;
				} else {
					failures++;
				}
			} catch (Exception e) {
				logger.error("Exception occured: ", e);
				failures++;
			}			
		}
		logger.info("Success: " + success + ", Failures: " + failures);
		
		if (success > 0 && failures == 0) {
			statusCode = Status.COMPLETED;
		} else if (success > 0 && failures > 0){
			statusCode = Status.COMPLETED_WITH_ERRORS;
		} else {
			statusCode = Status.FAILED;
		}
		
		Campaign updatedCampaign = new Campaign();
		updatedCampaign.setStatusCode(statusCode);
		
		campaignProxy.updateCampaignStatus(campaign.getId(), updatedCampaign);
		
		logger.info("Exiting updateEmailCampaign");
	}
}
