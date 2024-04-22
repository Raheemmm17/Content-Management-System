package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.UserDTO.ContributionPanelResponce;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class ContributionPanelController {

	private PanelService contributionService;

	public ContributionPanelController(PanelService contributionService) {
		super();
		this.contributionService = contributionService;
	}

	@Operation(description = "this endpoint is used to add User To ContributionPanel", responses = {
			@ApiResponse(responseCode = "200", description = "Blog is added Sucessfully "),
			@ApiResponse(responseCode = "404", description = "Blog is not added") })
	@PostMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponce>> addUserToContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.addUserToContributionPanel(userId, panelId);
	}

	@Operation(description = "this endpoint is used to remove User To ContributionPanel", responses = {
			@ApiResponse(responseCode = "200", description = "Blog is Found Sucessfully "),
			@ApiResponse(responseCode = "404", description = "Blog Id Not Found") })
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponce>> removeUserFromContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.removeUserFromContributionPanel(userId, panelId);
	}


}
