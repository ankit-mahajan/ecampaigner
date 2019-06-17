package com.ankit.ecampaigner.emailservice.email;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EMAILS")
public class Email implements Serializable {

	private static final long serialVersionUID = -6174960666112616492L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = true, updatable = false)
	private Long id;

	@NotNull
	private Long campaignId;
	
	private EmailStatus statusCode;
	
	@NotNull
	private String emailTo;

	protected Email() {
	}
	
	public Email(@NotNull Long campaignId, EmailStatus statusCode, @NotNull String emailTo) {
		super();
		this.campaignId = campaignId;
		this.statusCode = statusCode;
		this.emailTo = emailTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public EmailStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(EmailStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", campaignId=" + campaignId + ", statusCode=" + statusCode + ", emailTo=" + emailTo
				+ "]";
	}
}
