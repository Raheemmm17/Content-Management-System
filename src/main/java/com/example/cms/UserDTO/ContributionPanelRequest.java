package com.example.cms.UserDTO;

import java.util.List;

import com.example.cms.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContributionPanelRequest {
	private List<User> contributors;
}
