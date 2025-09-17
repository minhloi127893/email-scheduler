package com.javatest.email_scheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.javatest.email_scheduler.model.EmailSchedule;

public interface EmailScheduleRepository extends MongoRepository<EmailSchedule, String> {
	// Kiểm tra có schedule nào dùng template này không
    boolean existsByTemplateId(String templateId);
}
