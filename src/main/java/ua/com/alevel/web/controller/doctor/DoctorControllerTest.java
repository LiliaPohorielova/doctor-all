/*
package ua.com.alevel.web.controller.doctor;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/doctor")
public class DoctorController extends AbstractController {

    private HeaderName[] getColumnTitles() {
        return new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("lastname", "lastname", "lastname"),
                new HeaderName("firstname", "firstname", "firstname"),
                new HeaderName("middle name", "middleName", "middleName"),
                new HeaderName("created", "created", "created"),
                new HeaderName("details", null, null),
                new HeaderName("edit", null, null),
                new HeaderName("delete", null, null)
        };
    }

    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;

    public DoctorController(DoctorFacade doctorFacade, PatientFacade patientFacade) {
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnTitles = getColumnTitles();
        PageData<DoctorResponseDto> response = doctorFacade.findAll(webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/doctors/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Doctors");
        return "pages/doctor/doctor_all";

    }

    @GetMapping("/my_patients")
    public String findById(@PathVariable Long id, Model model) {
        Set<PatientResponseDto> patients = doctorFacade.getPatients(id);
        model.addAttribute("doctor", doctorFacade.findById(id));
        model.addAttribute("patients", patients);
        return "pages/doctor/my_patients";
    }

*/
/*    @GetMapping("/add/{id}")
    public String redirectToAddDoctorPage(@PathVariable Long id, Model model, WebRequest request) {
        List<DoctorResponseDto> doctors = doctorFacade.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("patient", patientFacade.findById(id));
        return "pages/doctor/doctor_add";
    }*//*


*/
/*    @GetMapping("/doctor/{patientId}/{doctorId}")
    public String addPatient(@PathVariable Long patientId, @PathVariable Long doctorId, Model model) {
        doctorFacade.addPatient(doctorId, patientId);
        List<DoctorResponseDto> doctors = patientFacade.getDoctors(patientId);
        model.addAttribute("patient", patientFacade.findById(patientId));
        model.addAttribute("doctors", doctors);
        return "pages/patient/patient_details";
    }

    @GetMapping("/delete/doctor/{patientId}/{doctorId}")
    public String deletePatientFromDoctor(@PathVariable Long patientId, @PathVariable Long doctorId, Model model) {
        doctorFacade.removePatient(doctorId, patientId);
        List<DoctorResponseDto> doctors = patientFacade.getDoctors(patientId);
        model.addAttribute("patient", patientFacade.findById(patientId));
        model.addAttribute("doctors", doctors);
        return "pages/patient/patient_details";
    }*//*


    private List<HeaderData> getHeaderDataList(HeaderName[] columnTitles, PageData<DoctorResponseDto> response) {
        List<HeaderData> headerDataList = new ArrayList<>();
        for (HeaderName headerName : columnTitles) {
            HeaderData data = new HeaderData();
            data.setHeaderName(headerName.getColumnName());
            if (StringUtils.isBlank(headerName.getTableName())) {
                data.setSortable(false);
            } else {
                data.setSortable(true);
                data.setSort(headerName.getDbName());
                if (response.getSort().equals(headerName.getDbName())) {
                    data.setActive(true);
                    data.setOrder(response.getOrder());
                } else {
                    data.setActive(false);
                    data.setOrder(DEFAULT_ORDER_PARAM_VALUE);
                }
            }
            headerDataList.add(data);
        }
        return headerDataList;
    }
}
*/
