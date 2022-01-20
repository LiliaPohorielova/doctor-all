package ua.com.alevel.web.controller.appointment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.slot.SlotService;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.appointment.AppointmentRequestDto;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientAppointmentController {

    private final PatientRegistrationFacade patientUserFacade;
    private final DoctorFacade doctorFacade;
    private final SlotFacade slotFacade;

    public PatientAppointmentController(PatientRegistrationFacade patientUserFacade, DoctorFacade doctorFacade, SlotFacade slotFacade) {
        this.patientUserFacade = patientUserFacade;
        this.doctorFacade = doctorFacade;
        this.slotFacade = slotFacade;
    }

    @GetMapping("/book_appointment")
    public String bookAppointment(Model model) {
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("appointmentForm", new AppointmentRequestDto());
        return "pages/appointment/book-appointments";
    }

    @RequestMapping(value = "/get_doctors/{specializationId}")
    @ResponseBody
    public List<String> getDoctors(@PathVariable("specializationId") Integer specializationId) {
        return doctorFacade.getDoctorsBySpecId(specializationId);
    }

    @RequestMapping(value = "/get_dates/{doctorId}")
    @ResponseBody
    public List<String> getDates(@PathVariable("doctorId") String doctorId) {
        return slotFacade.getDatesByDoctor(doctorId);
    }

    @RequestMapping(value = "/get_times/{doctorId}/{date}")
    @ResponseBody
    public List<String> getTimes(@PathVariable("doctorId") String doctorId, @PathVariable("date") String date) {
        return slotFacade.getTimeByDate(doctorId, date);
    }

    @PostMapping("/book_appointment")
    public String bookAppointment(@ModelAttribute("appointmentForm") AppointmentRequestDto appointmentForm, Model model){
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        Slot slot = slotFacade.getSlot(appointmentForm.getDoctor(), appointmentForm.getDate(), appointmentForm.getTime());
        slotFacade.bookSlot(slot.getId(),patient);
        return "pages/appointment/book-appointments";
    }
}
