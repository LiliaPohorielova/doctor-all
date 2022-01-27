package ua.com.alevel.web.controller.department;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.department.DepartmentFacade;

@Controller
@RequestMapping("/open/department")
public class DepartmentController {

    private final DepartmentFacade departmentFacade;

    public DepartmentController(DepartmentFacade departmentRepository) {
        this.departmentFacade = departmentRepository;
    }

    @GetMapping()
    public String redirectToAllDepartmentsPage(Model model) {
        model.addAttribute("departments", departmentFacade.findAll());
        return "pages/department/all_departments";
    }
}
