package ua.com.alevel.web.controller.elastic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.elastic.SearchDoctorFacade;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class SearchController {

    private final SearchDoctorFacade searchDoctorFacade;
    private final DoctorFacade doctorFacade;

    public SearchController(SearchDoctorFacade searchDoctorFacade, DoctorFacade doctorFacade) {
        this.searchDoctorFacade = searchDoctorFacade;
        this.doctorFacade = doctorFacade;
    }

    @GetMapping
    private String allDoctors(Model model, WebRequest webRequest) {
        model.addAttribute("doctorList", doctorFacade.search(webRequest));
        return "pages/patient/doctor_searched";
    }

    @PostMapping("/search")
    private String allDoctorsSearch(
            RedirectAttributes redirectAttributes, @RequestParam String doctorSearch) {
        redirectAttributes.addAttribute("doctorSearch", doctorSearch);
        return "redirect:/doctors";
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        System.out.println("OpenBookController.fetchSuggestions: " + query);
        return searchDoctorFacade.fetchSuggestions(query);
    }
}
