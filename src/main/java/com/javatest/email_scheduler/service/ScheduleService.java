package com.javatest.email_scheduler.service;

import com.javatest.email_scheduler.model.EmailSchedule;

public interface ScheduleService {

    EmailSchedule createSchedule(EmailSchedule schedule);

    void processSchedules();

    void sendEmail(EmailSchedule schedule);
}

