package com.javatest.email_scheduler.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailScheduleDTO {
	private String id;
	private String type; // có thể để String nếu muốn nhẹ
	private String cronExpression;
	private String timeConfig;
	private String recipients;
	private String templateId; 
}
