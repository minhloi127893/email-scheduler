package com.javatest.email_scheduler.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javatest.email_scheduler.common.Utils;
import com.javatest.email_scheduler.dto.EmailScheduleDTO;
import com.javatest.email_scheduler.model.EmailSchedule;
import com.javatest.email_scheduler.model.EmailTemplate;
import com.javatest.email_scheduler.repository.EmailScheduleRepository;
import com.javatest.email_scheduler.repository.EmailTemplateRepository;
import com.javatest.email_scheduler.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class EmailScheduleController {

	private final ScheduleService scheduleService;
	private final EmailScheduleRepository scheduleRepo;
	private final EmailTemplateRepository templateRepo;

	private final Utils utils;

	@GetMapping
	public String list(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<EmailSchedule> schedules = scheduleRepo.findAll(pageable);

		model.addAttribute("schedules", schedules);
		return "schedule-list";
	}

	@GetMapping("/new")
	public String createForm(Model model, RedirectAttributes redirectAttrs) {
		List<EmailTemplate> templates = templateRepo.findAll();

		if (templates.isEmpty()) {
			// Thông báo và redirect đến tạo template
			redirectAttrs.addFlashAttribute("message", "Please create a template first before creating a schedule.");
			return "redirect:/templates/new?redirectToSchedule=true";
		}
		model.addAttribute("schedule", new EmailSchedule());
		model.addAttribute("templates", templateRepo.findAll());
		return "schedule-form";
	}

	@PostMapping(consumes = "application/json")
	public String saveJson(@RequestBody EmailScheduleDTO req) {
		EmailSchedule schedule = utils.mapToEntity(req, EmailSchedule.class);
		scheduleService.createSchedule(schedule);
		return "redirect:/schedules";
	}

	@PostMapping(consumes = "application/x-www-form-urlencoded")
	public String saveForm(@ModelAttribute EmailSchedule schedule) {
		scheduleService.createSchedule(schedule);
		return "redirect:/schedules";
	}

	@GetMapping("/delete/{id}")
	public String deleteSchedule(@PathVariable String id, RedirectAttributes redirectAttrs) {
		scheduleRepo.deleteById(id);
		redirectAttrs.addFlashAttribute("success", "Schedule deleted successfully.");
		return "redirect:/schedules";
	}
	
	// 🔹 Edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        EmailSchedule schedule = scheduleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found: " + id));
        model.addAttribute("schedule", schedule);
        model.addAttribute("templates", templateRepo.findAll());
        return "schedule-form"; // dùng lại form create
    }

    // 🔹 Update (dùng chung @PostMapping hoặc tạo riêng @PostMapping("/update/{id}"))
    @PostMapping("/update/{id}")
    public String update(@PathVariable String id, @ModelAttribute EmailSchedule schedule) {
        schedule.setId(id); // giữ nguyên id cũ
        scheduleService.createSchedule(schedule); // sẽ generate lại cron nếu cần
        return "redirect:/schedules";
    }

}
