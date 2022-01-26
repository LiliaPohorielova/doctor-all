package ua.com.alevel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;

@Controller
public class MainController {

    @GetMapping
    public String main() {
        return "redirect:/open/dashboard";
    }
}
