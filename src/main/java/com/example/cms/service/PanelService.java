package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.UserDTO.ContributionPanelResponce;
import com.example.cms.utility.ResponseStructure;

public interface PanelService {
	ResponseEntity<ResponseStructure<ContributionPanelResponce>> addUserToContributionPanel(int userId , int panelId);

	ResponseEntity<ResponseStructure<ContributionPanelResponce>> removeUserFromContributionPanel(int userId , int panelId);
}
