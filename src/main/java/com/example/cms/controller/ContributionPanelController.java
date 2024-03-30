package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.UserDTO.ContributionPanelResponce;
import com.example.cms.model.ContributionPanel;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class ContributionPanelController {

	private PanelService contributionService;
	
	public ContributionPanelController(PanelService contributionService) {
		super();
		this.contributionService = contributionService;
	}

	@PostMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponce>> addUserToContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.addUserToContributionPanel(userId, panelId);
	}
	
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponce>> removeUserFromContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.removeUserFromContributionPanel(userId, panelId);
	}

	
}
