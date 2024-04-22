package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

public interface Panelrepository extends JpaRepository<ContributionPanel, Integer>{

	boolean existsByPanelIdAndContributors(int panelId, User user);

}
