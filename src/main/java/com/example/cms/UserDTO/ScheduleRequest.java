package com.example.cms.UserDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
	private LocalDateTime dateTime;
}
