package com.ankit.ecampaigner.campaignservice.campaign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "CAMPAIGNS")
public class Campaign implements Serializable {

	private static final long serialVersionUID = 7112354157625436227L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(insertable = true, updatable = false)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String campaignName;

	//@NotBlank
	private Status statusCode;
	
	@NotBlank
	private String subject;
	
	@NotBlank
	private String emailContent;

	@NotBlank
	private String emailTo;
	
	protected Campaign() {	
	}

	public Campaign(@NotBlank String campaignName, Status statusCode, @NotBlank String subject,
			@NotBlank String emailContent, @NotBlank String emailTo) {
		super();
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
		return "Campaign [id=" + id + ", campaignName=" + campaignName + ", statusCode=" + statusCode + ", subject="
				+ subject + ", emailContent=" + emailContent + ", emailTo=" + emailTo + "]";
	}
}
