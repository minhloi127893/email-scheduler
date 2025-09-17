package com.javatest.email_scheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.javatest.email_scheduler.model.EmailTemplate;

public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {
    boolean existsByIdAndActiveTrue(Long id);
}
