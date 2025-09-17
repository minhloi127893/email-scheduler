package com.javatest.email_scheduler.common;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.javatest.email_scheduler.common.enums.FrequencyType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CronGenerator {

    public static String generate(FrequencyType type, String timeConfig) {
        switch (type) {
            case DAILY:
                return dailyCron(timeConfig);
            case WEEKLY:
                return weeklyCron(timeConfig);
            case MONTHLY:
                return monthlyCron(timeConfig);
            default:
                throw new IllegalArgumentException("Unsupported type for cron generation: " + type);
        }
    }

    private static String dailyCron(String time) {
        LocalTime lt = LocalTime.parse(time); // "09:00"
        return String.format("0 %d %d * * ?", lt.getMinute(), lt.getHour());
    }

    private static String weeklyCron(String config) {
        // format: "MONDAY 09:00"
        String[] parts = config.split(" ");
        DayOfWeek day = DayOfWeek.valueOf(parts[0].toUpperCase()); // MONDAY
        LocalTime lt = LocalTime.parse(parts[1]);                  // 09:00
        String cronDay = day.name().substring(0, 3);               // MON
        return String.format("0 %d %d ? * %s", lt.getMinute(), lt.getHour(), cronDay);
    }

    private static String monthlyCron(String config) {
        // format: "1 08:30" (ngày 1 lúc 08:30)
        String[] parts = config.split(" ");
        int dayOfMonth = Integer.parseInt(parts[0]);    // 1
        LocalTime lt = LocalTime.parse(parts[1]);       // 08:30
        return String.format("0 %d %d %d * ?", lt.getMinute(), lt.getHour(), dayOfMonth);
    }
}
