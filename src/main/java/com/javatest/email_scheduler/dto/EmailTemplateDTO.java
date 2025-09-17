package com.javatest.email_scheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailTemplateDTO {

	private String id;

	private String name;

	private String subject;

	private String body;

	private String createDateTime;
	
	private boolean active;

}
