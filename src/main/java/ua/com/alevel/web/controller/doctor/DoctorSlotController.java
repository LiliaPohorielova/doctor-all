package ua.com.alevel.web.controller.doctor;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doctor/slots")
public class DoctorSlotController {

    private final SlotFacade slotFacade;

    public DoctorSlotController(SlotFacade slotFacade) {
        this.slotFacade = slotFacade;
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/doctor/slots", model);
    }

    @GetMapping("/new")
    public String redirectToNewSlotPage(Model model) {
        model.addAttribute("slot", new SlotRequestDto());
        return "pages/slot/slot_new";
    }

    @PostMapping("/new")
    public String createNewSlot(@ModelAttribute("slot") SlotRequestDto slotRequestDto) {
        slotFacade.create(slotRequestDto);
        return "redirect:/doctor/slots";
    }

/*    @GetMapping("/update/{id}")
    public String redirectToUpdateSlotPage(@PathVariable Long id, Model model) {
        SlotResponseDto slotResponseDto = slotFacade.findById(id);
        model.addAttribute("slot", slotResponseDto);
        return "pages/slot/slot_update";
    }

    @PostMapping("/update/{id}")
    public String updateSlot(@ModelAttribute("slot") SlotRequestDto slotRequestDto, @PathVariable Long id) {
        slotFacade.update(slotRequestDto, id);
        return "redirect:/doctor/slots";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        List<PatientResponseDto> patients = doctorFacade.getPatients(doctor.getId());
        List<SlotResponseDto> doctors = slotFacade.getDoctors(id);
        model.addAttribute("slot", slotFacade.findById(id));
        return "pages/slot/slot_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        List<DoctorResponseDto> doctors = slotFacade.getDoctors(id);
        for (DoctorResponseDto doctor: doctors) {
            doctorFacade.removeSlot(doctor.getId(), id);
        }
        slotFacade.delete(id);
        return "redirect:/doctor/slots";
    }*/
}
