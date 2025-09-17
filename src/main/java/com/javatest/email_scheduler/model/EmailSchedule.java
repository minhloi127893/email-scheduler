package com.javatest.email_scheduler.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.javatest.email_scheduler.common.enums.FrequencyType;
import com.javatest.email_scheduler.common.enums.STATUS;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "email_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSchedule {
	
    @Id
    private String id;

    private FrequencyType type;  // DAILY, WEEKLY, MONTHLY, CRON

    private String cronExpression;

    private String timeConfig;

    private String recipients;

    private String templateId; // reference tới EmailTemplate
    
    private String name;
    
    private STATUS status;
    
    private LocalDateTime createdDatetime = LocalDateTime.now();
    
}
