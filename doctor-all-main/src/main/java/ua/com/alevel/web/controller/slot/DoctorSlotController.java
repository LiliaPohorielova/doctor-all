package ua.com.alevel.web.controller.slot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.facade.slot.SlotValidationFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.List;

@Controller
@RequestMapping("/doctor/slots")
public class DoctorSlotController extends AbstractController {

    private final SlotFacade slotFacade;
    private final DoctorRegistrationFacade doctorUserFacade;
    private final DoctorFacade doctorFacade;
    private final SlotValidationFacade slotValidationFacade;
    private final PatientFacade patientFacade;

    public DoctorSlotController(SlotFacade slotFacade, DoctorRegistrationFacade doctorUserFacade, DoctorFacade doctorFacade, SlotValidationFacade slotValidationFacade, PatientFacade patientFacade) {
        this.slotFacade = slotFacade;
        this.doctorUserFacade = doctorUserFacade;
        this.doctorFacade = doctorFacade;
        this.slotValidationFacade = slotValidationFacade;
        this.patientFacade = patientFacade;
    }

    @GetMapping("/new")
    public String redirectToNewSlotPage(Model model) {
        model.addAttribute("slot", new SlotRequestDto());
        return "pages/slot/slot_new";
    }

    @PostMapping("/new")
    public String createNewSlot(@ModelAttribute("slot") SlotRequestDto slotRequestDto, Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        slotRequestDto.setDoctor(doctor);
        slotRequestDto.setStatus(SlotStatus.FREE);
        String err = slotValidationFacade.validateSlot(slotRequestDto);
        if (!err.isEmpty()) {
            showError(model, "Such slot is already exist!!! Choose another time or date!");
            return "pages/slot/slot_new";
        }
        slotFacade.create(slotRequestDto);
        return "redirect:/doctor/slots";
    }

    @GetMapping("/delete_slot/{slotId}")
    public String deleteById(@PathVariable Long slotId, Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        doctorFacade.removeSlot(doctor.getId(), slotId);
        return "redirect:/doctor/slots";
    }

    @GetMapping()
    public String mySlots(Model model, String error) {
        //showMessage(model, false);
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        //Set<SlotResponseDto> slots = doctorFacade.getSlots(doctor.getId());
        List<Slot> freeSlots = slotFacade.findSlotByDoctor(SlotStatus.FREE, doctor.getId());
        List<Slot> bookSlots = slotFacade.findSlotByDoctor(SlotStatus.BOOKED, doctor.getId());
        List<Slot> pastSlots = slotFacade.findSlotByDoctor(SlotStatus.PAST, doctor.getId());
        List<Slot> cancelledSlots = slotFacade.findSlotByDoctor(SlotStatus.CANCELLED, doctor.getId());
        model.addAttribute("freeSlots", freeSlots);
        model.addAttribute("bookSlots", bookSlots);
        model.addAttribute("pastSlots", pastSlots);
        model.addAttribute("cancelledSlots", cancelledSlots);
        if (error!=null) {
            showError(model, "You can't mark this visit as past. The past hasn't arrived yet :)");
        }
        return "pages/slot/slots";
    }

    @GetMapping("/error")
    public String mySlots(Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        List<Slot> freeSlots = slotFacade.findSlotByDoctor(SlotStatus.FREE, doctor.getId());
        List<Slot> bookSlots = slotFacade.findSlotByDoctor(SlotStatus.BOOKED, doctor.getId());
        List<Slot> pastSlots = slotFacade.findSlotByDoctor(SlotStatus.PAST, doctor.getId());
        List<Slot> cancelledSlots = slotFacade.findSlotByDoctor(SlotStatus.CANCELLED, doctor.getId());
        model.addAttribute("freeSlots", freeSlots);
        model.addAttribute("bookSlots", bookSlots);
        model.addAttribute("pastSlots", pastSlots);
        model.addAttribute("cancelledSlots", cancelledSlots);
        showError(model, "You can't mark this visit as past. The past hasn't arrived yet :)");

        return "pages/slot/slots";
    }

    @GetMapping("/check_slot/{slotId}")
    public String checkAsPast(@PathVariable Long slotId, Model model) {
        String err = slotValidationFacade.validatePastSlot(slotId);
        if (!err.isEmpty()) {
            return "redirect:/doctor/slots/error";
        }
        slotFacade.updateStatus(slotId, SlotStatus.PAST);
        return "redirect:/doctor/slots";
    }

    @GetMapping("/all_patients/{slotId}")
    public String redirectToAddPatientPage(@PathVariable Long slotId, Model model) {
        List<PatientResponseDto> patients = patientFacade.findAll();
        model.addAttribute("patients", patients);
        model.addAttribute("slotId", slotId);
        return "pages/slot/slot_patient_add";
    }

    @GetMapping("/add_patient/{slotId}/{patientId}")
    public String addPatient(@PathVariable Long slotId, @PathVariable Long patientId) {
        patientFacade.addAppointment(slotFacade.bookSlot(slotId, patientId).getId(), patientId);
        return "redirect:/doctor/slots";
    }
}
