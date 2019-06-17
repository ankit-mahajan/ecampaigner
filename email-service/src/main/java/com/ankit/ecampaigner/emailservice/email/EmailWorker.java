package com.ankit.ecampaigner.emailservice.email;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailWorker implements Callable<EmailStatus> {

	protected Logger logger = LoggerFactory.getLogger(EmailWorker.class);

	private EmailService emailService;
	private EmailRepository emailRepository;	
	private Email email;
	private Campaign campaign;
	
	public EmailWorker(Email email, Campaign campaign, EmailService emailService, EmailRepository emailRepository) {
		super();
		this.email = email;
		this.campaign = campaign;
		this.emailService = emailService;
		this.emailRepository = emailRepository;
	}

	@Override
	public EmailStatus call() throws Exception {
		logger.info("Entering call");
		
		String emailTo = this.email.getEmailTo();
		String subject = this.campaign.getSubject();
		String content = this.campaign.getEmailContent();
		
		Email savedEmail = null;
		try {
			savedEmail = emailRepository.save(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("===> " + content);
		content = content.replace("$USER$", emailTo);
		logger.info("Content after replacement: {}", content);
		
		logger.info("==> " + emailTo + subject + content);
		EmailStatus status = emailService.sendMail(emailTo, subject, content);
			
		savedEmail.setStatusCode(status);
		emailRepository.save(savedEmail);
		
		logger.info("Exiting call");
		return status;
	}
}
