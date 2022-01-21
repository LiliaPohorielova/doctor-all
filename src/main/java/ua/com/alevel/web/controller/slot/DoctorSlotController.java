package ua.com.alevel.web.controller.slot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;

import java.util.List;

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
        //Set<SlotResponseDto> slots = doctorFacade.getSlots(doctor.getId());
        List<Slot> freeSlots = slotFacade.findSlotByDoctor(SlotStatus.FREE,doctor.getId());
        List<Slot> bookSlots = slotFacade.findSlotByDoctor(SlotStatus.BOOKED,doctor.getId());
        List<Slot> pastSlots = slotFacade.findSlotByDoctor(SlotStatus.PAST,doctor.getId());
        List<Slot> cancelledSlots = slotFacade.findSlotByDoctor(SlotStatus.CANCELLED,doctor.getId());
        model.addAttribute("freeSlots", freeSlots);
        model.addAttribute("bookSlots", bookSlots);
        model.addAttribute("pastSlots", pastSlots);
        model.addAttribute("cancelledSlots", cancelledSlots);
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

    @GetMapping("/check_slot/{slotId}")
    public String deleteById(@PathVariable Long slotId) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        slotFacade.updateStatus(slotId, SlotStatus.PAST);
        return "redirect:/doctor/slots";
    }
}
