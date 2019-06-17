package com.ankit.ecampaigner.emailservice.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Service
public class EmailServiceImpl implements EmailService {

	protected Logger logger = LoggerFactory.getLogger(EmailWorker.class);

	@Value("${email.from}")
	private String emailFrom;

	@Override
	public EmailStatus sendMail(String emailTo, String subject, String content) {
		logger.info("Entering sendMail. {}, {}, {}", emailTo, subject, content);

		EmailStatus status = EmailStatus.IN_PROGRESS;

		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_EAST_1).build();

			Message message = new Message();
			message.setSubject(new Content().withCharset("UTF-8").withData(subject));
			message.setBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(content)));

			Destination destination = new Destination().withToAddresses(emailTo);

			SendEmailRequest request = new SendEmailRequest().withDestination(destination).withMessage(message)
					.withSource(emailFrom);

			client.sendEmail(request);
			status = EmailStatus.SUCCESS;
		} catch (Exception ex) {
			status = EmailStatus.FAILED;
			logger.error("Exception: ", ex);
		}

		logger.info("Existing sendMail: {}", status);
		return status;
	}
}
