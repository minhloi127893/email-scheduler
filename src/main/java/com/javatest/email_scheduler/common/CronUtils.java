package com.javatest.email_scheduler.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.quartz.CronExpression;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CronUtils {

    public static boolean shouldRunNow(String cronExpression, LocalDateTime now) {
        try {
            CronExpression cron = new CronExpression(cronExpression);
            Date nextValid = cron.getNextValidTimeAfter(Timestamp.valueOf(now.minusMinutes(1)));
            if (nextValid == null) return false;
            return !nextValid.after(Timestamp.valueOf(now));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cron expression: " + cronExpression, e);
        }
    }
}
