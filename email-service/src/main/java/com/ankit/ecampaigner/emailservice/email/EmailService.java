package com.ankit.ecampaigner.emailservice.email;

public interface EmailService {

	EmailStatus sendMail(String emailTo, String subject, String content);
}
