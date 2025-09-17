package com.javatest.email_scheduler.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.javatest.email_scheduler.dto.EmailTemplateDTO;
import com.javatest.email_scheduler.model.EmailSchedule;
import com.javatest.email_scheduler.model.EmailTemplate;
import com.javatest.email_scheduler.repository.EmailScheduleRepository;
import com.javatest.email_scheduler.repository.EmailTemplateRepository;
import com.javatest.email_scheduler.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/templates")
@RequiredArgsConstructor
public class EmailTemplateController {

	private final EmailTemplateRepository templateRepo;
	private final EmailScheduleRepository scheduleRepo;

	private final ScheduleService emailService;

	private final Utils utils;

	@GetMapping
	public String list(Model model) {
		List<EmailTemplate> templates = templateRepo.findAll();
		List<String> usedTemplateIds = scheduleRepo.findAll().stream().map(EmailSchedule::getTemplateId).distinct()
				.collect(Collectors.toList());

		model.addAttribute("templates", templates);
		model.addAttribute("usedTemplateIds", usedTemplateIds);
		return "template-list";
	}

	@GetMapping("/new")
	public String createForm(@RequestParam(value = "redirectToSchedule", required = false) Boolean redirectToSchedule,
			Model model) {
		model.addAttribute("template", new EmailTemplate());
		model.addAttribute("redirectToSchedule", redirectToSchedule != null && redirectToSchedule);
		return "template-form";
	}

	// Cho JSON
	@PostMapping(consumes = "application/json")
	public String saveJson(@RequestBody EmailTemplateDTO template) {
		EmailTemplate entity = utils.mapToEntity(template, EmailTemplate.class);
		templateRepo.save(entity);
		return "redirect:/templates";
	}

	// Cho Form data (ví dụ submit từ Thymeleaf)
	@PostMapping(consumes = "application/x-www-form-urlencoded")
	public String saveForm(@ModelAttribute EmailTemplateDTO template,
			@RequestParam(value = "redirectToSchedule", required = false) Boolean redirectToSchedule) {
		EmailTemplate entity = utils.mapToEntity(template, EmailTemplate.class);
		templateRepo.save(entity);
		if (redirectToSchedule != null && redirectToSchedule) {
			return "redirect:/schedules/new";
		}
		return "redirect:/templates";
	}

	@GetMapping("/delete/{id}")
	public String deleteTemplate(@PathVariable String id, RedirectAttributes redirectAttrs) {
		boolean used = scheduleRepo.existsByTemplateId(id);
		if (used) {
			redirectAttrs.addFlashAttribute("error", "Cannot delete template because it is used by schedules.");
			return "redirect:/templates";
		}
		templateRepo.deleteById(id);
		redirectAttrs.addFlashAttribute("success", "Template deleted successfully.");
		return "redirect:/templates";
	}

	// 🔹 Edit form
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable String id, Model model) {
		EmailTemplate template = templateRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Template not found: " + id));
		model.addAttribute("template", template);
		return "template-form"; // dùng lại form chung
	}

	// 🔹 Update template
	@PostMapping("/update/{id}")
	public String update(@PathVariable String id, @ModelAttribute EmailTemplate template) {
		template.setId(id);
		templateRepo.save(template);
		return "redirect:/templates";
	}
}
