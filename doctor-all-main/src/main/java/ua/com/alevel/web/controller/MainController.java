package ua.com.alevel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping
    public String main() {
        return "redirect:/open/dashboard";
    }
}
