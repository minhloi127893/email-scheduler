package com.javatest.email_scheduler.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.javatest.email_scheduler.common.CronGenerator;
import com.javatest.email_scheduler.common.CronUtils;
import com.javatest.email_scheduler.common.enums.FrequencyType;
import com.javatest.email_scheduler.model.EmailSchedule;
import com.javatest.email_scheduler.model.EmailTemplate;
import com.javatest.email_scheduler.repository.EmailScheduleRepository;
import com.javatest.email_scheduler.repository.EmailTemplateRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final EmailScheduleRepository scheduleRepo;
    private final EmailTemplateRepository templateRepo;
    private final JavaMailSender mailSender;

    @Override
    public EmailSchedule createSchedule(EmailSchedule schedule) {
        if (schedule.getType() != FrequencyType.CRON) {
            String cron = CronGenerator.generate(schedule.getType(), schedule.getTimeConfig());
            schedule.setCronExpression(cron);
        }
        return scheduleRepo.save(schedule);
    }

    @Override
    @Scheduled(cron = "0 * * * * ?") // mỗi phút check 1 lần
    public void processSchedules() {
        LocalDateTime now = LocalDateTime.now();
        List<EmailSchedule> schedules = scheduleRepo.findAll();
        for (EmailSchedule s : schedules) {
            if (CronUtils.shouldRunNow(s.getCronExpression(), now)) {
                sendEmail(s);
            }
        }
    }

    @Override
    public void sendEmail(EmailSchedule schedule) {
    	 EmailTemplate template = templateRepo.findById(schedule.getTemplateId())
                 .orElseThrow(() -> new IllegalArgumentException("Template not found: " + schedule.getTemplateId()));

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(schedule.getRecipients().split(","));
        msg.setSubject(template.getSubject());
        msg.setText(template.getBody());

        mailSender.send(msg);
    }
    
    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true = HTML
        mailSender.send(message);
    }

}


