package com.ankit.ecampaigner.emailservice.email;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;

@FeignClient(name = "zuul-api-server")
@RibbonClient(name = "campaign-service")
public interface CampaignServiceProxy {

	@GetMapping("campaign-service/ecampaigner/campaigns/{id}")
	public Campaign getCampaign(@PathVariable Long id);

	@Headers("Content-Type: application/json")
	@PutMapping("campaign-service/ecampaigner/campaigns/{id}")
	public Campaign updateCampaign(@PathVariable Long id, @RequestBody Campaign campaign);

	@Headers("Content-Type: application/json")
	@PatchMapping("campaign-service/ecampaigner/campaigns/{id}")
	public Campaign updateCampaignStatus(@PathVariable Long id, @RequestBody Campaign campaign);
}
