package ua.com.alevel.web.controller.slot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.util.Set;

@Controller
@RequestMapping("/doctor/slots")
public class DoctorSlotController {

    private final SlotFacade slotFacade;
    private final DoctorRegistrationFacade doctorUserFacade;
    private final DoctorFacade doctorFacade;

    public DoctorSlotController(SlotFacade slotFacade, DoctorRegistrationFacade doctorUserFacade, DoctorFacade doctorFacade) {
        this.slotFacade = slotFacade;
        this.doctorUserFacade = doctorUserFacade;
        this.doctorFacade = doctorFacade;
    }

    @GetMapping()
    public String mySlots(Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        Set<SlotResponseDto> slots = doctorFacade.getSlots(doctor.getId());
        model.addAttribute("slots", slots);
        return "pages/slot/slots";
    }

    @GetMapping("/new")
    public String redirectToNewSlotPage(Model model) {
        model.addAttribute("slot", new SlotRequestDto());
        return "pages/slot/slot_new";
    }

    @PostMapping("/new")
    public String createNewSlot(@ModelAttribute("slot") SlotRequestDto slotRequestDto) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        slotRequestDto.setDoctor(doctor);
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
}
