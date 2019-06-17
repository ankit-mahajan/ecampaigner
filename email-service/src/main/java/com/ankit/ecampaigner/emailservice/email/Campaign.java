package com.ankit.ecampaigner.emailservice.email;

import java.io.Serializable;

public class Campaign implements Serializable {

	private static final long serialVersionUID = -6393974799664542430L;

	private Long id;
	private String campaignName;
	private Status statusCode;
	private String subject;
	private String emailContent;
	private String emailTo;

	protected Campaign() {
	}

	public Campaign(Long id, String campaignName, Status statusCode, String subject, String emailContent, String emailTo) {
		super();
		this.id = id;
		this.campaignName = campaignName;
		this.statusCode = statusCode;
		this.subject = subject;
		this.emailContent = emailContent;
		this.emailTo = emailTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Status getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Status statusCode) {
		this.statusCode = statusCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	@Override
	public String toString() {
		return "CampaignMessage [id=" + id + ", campaignName=" + campaignName + ", statusCode=" + statusCode
				+ ", emailContent=" + emailContent + ", emailTo=" + emailTo + "]";
	}
}
