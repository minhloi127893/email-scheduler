package com.javatest.email_scheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.javatest.email_scheduler.common.enums.STATUS;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Users")
@Data
@NoArgsConstructor
public class Users {
	
    @Id
    private String id;

    private String name;
    
    private String email;

    private STATUS status;

    private String createdDatetime;
    
    private String updateDatetime;
    
    private boolean active = true;
}
