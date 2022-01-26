package ua.com.alevel.web.controller.department;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;

@Controller
@RequestMapping("/open/department")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping()
    public String redirectToAllDepartmentsPage(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "pages/department/all_departments";
    }
}
