package com.javatest.email_scheduler.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "email_template")
@Data
@NoArgsConstructor
public class EmailTemplate {
	
    @Id
    private String id;

    private String name;
    
    private String subject;

    private String body;

    private LocalDateTime createdDatetime = LocalDateTime.now();
    
    private boolean active = true;
    
}
