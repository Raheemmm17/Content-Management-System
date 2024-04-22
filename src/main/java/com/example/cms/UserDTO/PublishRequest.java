package com.example.cms.UserDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PublishRequest {
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;

	private ScheduleRequest schedule;
}
