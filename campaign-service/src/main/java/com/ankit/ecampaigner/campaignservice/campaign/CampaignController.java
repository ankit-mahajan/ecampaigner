package com.ankit.ecampaigner.campaignservice.campaign;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.ecampaigner.campaignservice.exception.CampaignNotFoundException;

@RestController
@RequestMapping("ecampaigner")
public class CampaignController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private CampaignEventProducer producer;
	
	@GetMapping("/campaigns")
	public ResponseEntity<List<Campaign>> getAllCampaigns() {
		List<Campaign> campaigns = campaignRepository.findAll();
		return new ResponseEntity<List<Campaign>>(campaigns, HttpStatus.OK);
	}
	
	@GetMapping("/campaigns/{id}")
	public ResponseEntity<Campaign> getCampaign(@PathVariable Long id) {
		Optional<Campaign> campaigns = campaignRepository.findById(id);
		if (!campaigns.isPresent()) {
			throw new CampaignNotFoundException("id: " + id);
		}
		Campaign campaign = campaigns.get();
		return new ResponseEntity<Campaign>(campaign, HttpStatus.OK);
	}
	
	@PostMapping("/campaigns")
	public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign) {
		Campaign savedCampaign = campaignRepository.save(campaign);
		return new ResponseEntity<Campaign>(savedCampaign, HttpStatus.CREATED);
	}
	
	@PutMapping("campaigns/{id}")
	public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign updatedCampaign) {

		Optional<Campaign> campaigns = campaignRepository.findById(id);

		if (campaigns.isPresent()) {
			updatedCampaign.setId(id);
		} else {
			updatedCampaign.setId(null);
		}

		Campaign savedCampaign = campaignRepository.save(updatedCampaign);
		return ResponseEntity.ok().body(savedCampaign);
	}

	@PutMapping("campaigns/{id}/submit")
	public ResponseEntity<Campaign> submitCampaign(@PathVariable Long id) {

		Optional<Campaign> campaigns = campaignRepository.findById(id);
		if (!campaigns.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Campaign campaign = campaigns.get();
		campaign.setStatusCode(Status.SUBMITTED);
		Campaign savedCampaign = campaignRepository.save(campaign);

		try {
			producer.produce(savedCampaign);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.ok().body(savedCampaign);
	}
	
	@PatchMapping("campaigns/{id}")
	public ResponseEntity<Campaign> patchCampaign(@PathVariable Long id, @RequestBody Campaign updatedCampaign) {

		Optional<Campaign> campaigns = campaignRepository.findById(id);
		if (!campaigns.isPresent()) {
			throw new CampaignNotFoundException("Campaign Not Found. id: " + id);
		}

		Campaign campaign = campaigns.get();

		BeanWrapper srcWrapper = PropertyAccessorFactory.forBeanPropertyAccess(updatedCampaign);
		BeanWrapper targetWrapper = PropertyAccessorFactory.forBeanPropertyAccess(campaign);

		JSONObject jsonObject = new JSONObject(updatedCampaign);
		Iterator<String> propertyIterator = jsonObject.keys();

		while (propertyIterator.hasNext()) {
			String property = propertyIterator.next();
			if ("statusCode".equals(property)) {
				Status statusCode = (Status) jsonObject.get("statusCode");
				campaign.setStatusCode(statusCode);
			} else {
				targetWrapper.setPropertyValue(property, srcWrapper.getPropertyValue(property));
			}
		}

		campaign.setId(id);
		campaignRepository.save(campaign);

		return ResponseEntity.ok().body(campaign);
	}
}
