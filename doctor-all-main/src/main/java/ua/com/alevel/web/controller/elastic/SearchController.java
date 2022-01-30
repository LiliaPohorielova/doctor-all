package ua.com.alevel.web.controller.elastic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.elastic.SearchDoctorFacade;
import ua.com.alevel.facade.elastic.SearchPatientFacade;
import ua.com.alevel.facade.patient.PatientFacade;

import java.util.List;

@Controller
public class SearchController {

    private final SearchDoctorFacade searchDoctorFacade;
    private final SearchPatientFacade searchPatientFacade;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;

    public SearchController(SearchDoctorFacade searchDoctorFacade, SearchPatientFacade searchPatientFacade, DoctorFacade doctorFacade, PatientFacade patientFacade) {
        this.searchDoctorFacade = searchDoctorFacade;
        this.searchPatientFacade = searchPatientFacade;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
    }

    @GetMapping("/doctors")
    private String allDoctors(Model model, WebRequest webRequest) {
        model.addAttribute("doctorList", doctorFacade.search(webRequest));
        return "pages/patient/doctor_searched";
    }

    @PostMapping("/doctors/search")
    private String allDoctorsSearch(
            RedirectAttributes redirectAttributes, @RequestParam String doctorSearch) {
        redirectAttributes.addAttribute("doctorSearch", doctorSearch);
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        System.out.println("SearchController.fetchSuggestions: " + query);
        return searchDoctorFacade.fetchSuggestions(query);
    }

    @GetMapping("/patients")
    private String allPatients(Model model, WebRequest webRequest) {
        model.addAttribute("patientList", patientFacade.search(webRequest));
        return "pages/doctor/patient_searched";
    }

    @PostMapping("/patients/search")
    private String allPatientsSearch(
            RedirectAttributes redirectAttributes, @RequestParam String patientSearch) {
        redirectAttributes.addAttribute("patientSearch", patientSearch);
        return "redirect:/patients";
    }

    @GetMapping("/patients/suggestions")
    @ResponseBody
    public List<String> fetchPatientSuggestions(@RequestParam(value = "q", required = false) String query) {
        System.out.println("SearchController.fetchSuggestions: " + query);
        return searchPatientFacade.fetchSuggestions(query);
    }
}
