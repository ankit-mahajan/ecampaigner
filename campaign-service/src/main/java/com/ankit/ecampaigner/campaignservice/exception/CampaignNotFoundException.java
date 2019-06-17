package com.ankit.ecampaigner.campaignservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CampaignNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2605728945483710162L;

	public CampaignNotFoundException(String message) {
		super(message);
	}
}
