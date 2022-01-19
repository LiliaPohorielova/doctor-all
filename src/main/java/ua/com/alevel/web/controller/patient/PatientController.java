package ua.com.alevel.web.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    private final PatientRegistrationFacade patientUserFacade;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;

    public PatientController(PatientRegistrationFacade patientUserFacade, DoctorFacade doctorFacade, PatientFacade patientFacade) {
        this.patientUserFacade = patientUserFacade;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        model.addAttribute("patient",patient);
        return "pages/patient/dashboard";
    }

    @GetMapping("/my_doctors")
    public String myPatients(WebRequest webRequest, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(patient.getId());
        model.addAttribute("doctors", doctors);
        return "pages/patient/my_doctors";
    }

    @GetMapping("/all_doctors")
    public String redirectToAddDoctorPage(Model model) {
        List<DoctorResponseDto> doctors = doctorFacade.findAll();
        model.addAttribute("doctors", doctors);
        return "pages/patient/patient_doctor_add";
    }

    @GetMapping("/add_doctor/{doctorId}")
    public String addDoctor(@PathVariable Long doctorId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        doctorFacade.addPatient(doctorId, patient.getId());
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(patient.getId());
        model.addAttribute("doctors", doctors);
        return "pages/patient/my_doctors";
    }

    @GetMapping("/delete_doctor/{doctorId}")
    public String deleteDoctorFromPatient(@PathVariable Long doctorId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        doctorFacade.removePatient(doctorId, patient.getId());
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(patient.getId());
        model.addAttribute("doctors", doctors);
        return "pages/patient/my_doctors";
    }

    @GetMapping("/about_doctor/{doctorId}")
    public String detailsByDoctorId(@PathVariable Long doctorId, Model model) {
        model.addAttribute("doctor", doctorFacade.findById(doctorId));
        return "pages/patient/about_doctor";
    }

    @GetMapping("/book_appointment")
    public String bookAppointment(Model model) {
        List<DoctorResponseDto> doctors = doctorFacade.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("appointmentForm", new PatientAppointment());
        model.addAttribute("specializationList", DoctorSpecialization.values());
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);

        return "pages/appointment/book-appointments";
    }

    @RequestMapping(value = "/patient/book_appointment/{specializationId}", method = RequestMethod.GET)
    public String getDoctorsBySpecialization(@PathVariable("specializationId") Long specializationId, Model model) {
        /*model.addAttribute("insuranceCompanies", insuranceCompanyService
                .getInsuranceCompaniesByTfoms(new TerritorialFondOms(tfomsId)));*/
        return "pages/appointment/book-appointments";
    }

    @RequestMapping(value = "/get_doctors/{specializationId}")
    @ResponseBody
    /*public Set getRegions(@RequestParam Long specializationId) {*/
/*    public Set getRegions(@PathVariable("specializationId") Long specializationId) {
        Map<Long, Set<String>> doctors = doctorFacade.getDoctorsAndSpec();
        return doctors.get(specializationId);
    }*/
    public List getDoctors(@PathVariable("specializationId") Integer specializationId) {
        List<String> doctors = doctorFacade.getDoctorsBySpecId(specializationId);
        return doctors;
    }

    @RequestMapping(value = "/get_dates/{doctorId}")
    @ResponseBody
    public List getDates(@PathVariable("doctorId") String doctorId) {
        List<String> dates = doctorFacade.getDatesByDoctor(doctorId);
        return dates;
    }

    @RequestMapping(value = "/get_times/{doctorId}/{date}")
    @ResponseBody
    public List getTimes(@PathVariable("doctorId") String doctorId, @PathVariable("date") String date) {
        List<String> times = doctorFacade.getTimeByDate(doctorId, date);
        return times;
    }

/*    @PostMapping("/book_appointment")
    public String bookAppointment(@ModelAttribute("appointmentForm") AppointmentForm appointmentForm, Model model){

        List<Doctor> doctors = doctorService.findBySpecialization(Integer.parseInt(appointmentForm.getSpecialization()));

        List<Appointment> availableAppointments = new ArrayList<>();

        for (Doctor doctor: doctors) {
            List<Appointment> searchedAppointments = appointmentService.getAppointment(doctor,workplaceService.findById(Integer.parseInt(appointmentForm.getLocation())), appointmentForm.getDate());
            if (!searchedAppointments.isEmpty())
                availableAppointments = searchedAppointments;
        }

        model.addAttribute("availableAppointments",availableAppointments);
        model.addAttribute("appointmentForm",appointmentForm);
        model.addAttribute("specializationList",specializationService.getAll());
        model.addAttribute("workplaceList",workplaceService.getAll());
        return "pages/appointment/book-appointments";
    }

    @PostMapping("/choosehour")
    public String choosehour(@ModelAttribute("appointmentForm") AppointmentForm appointmentForm, @RequestParam(value = "id") Integer id, Authentication auth, BindingResult bindingResult, Model model){
        String user = auth.getName();
        LoginData loginData = loginService.findByEmail(user);
        Patient patient = loginData.getPatient();

        appointmentService.bookAppointment(id,patient);

        return "redirect:/patient/dashboard";
    }*/
}
