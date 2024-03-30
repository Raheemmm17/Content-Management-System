package com.example.cms.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ContributionPanel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int panelId;
	
	@ManyToMany
	private List<User> users;
}
