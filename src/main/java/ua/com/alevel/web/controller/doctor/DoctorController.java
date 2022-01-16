package ua.com.alevel.web.controller.doctor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/doctor")
public class DoctorController extends AbstractController {
    
    private final DoctorRegistrationFacade doctorUserFacade;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;

    public DoctorController(DoctorRegistrationFacade doctorUserFacade, DoctorFacade doctorFacade, PatientFacade patientFacade) {
        this.doctorUserFacade = doctorUserFacade;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
    }

    private HeaderName[] getPatientsTitles() {
        return new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("lastname", "lastname", "lastname"),
                new HeaderName("firstname", "firstname", "firstname"),
                new HeaderName("gender", "gender", "gender"),
                new HeaderName("email", "patient_user_id", "patientUser"),
                new HeaderName("details", null, null),
                new HeaderName("delete", null, null)
        };
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        model.addAttribute("doctor",doctor);
        return "pages/doctor/dashboard";
    }

    @GetMapping("/my_patients")
    public String myPatients(WebRequest webRequest, Model model) {

        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        Set<PatientResponseDto> patients = doctorFacade.getPatients(doctor.getId());
        model.addAttribute("patients", patients);
        //TODO: Table patients WebRequest
        /*HeaderName[] columnTitles = getPatientsTitles();
        PageData<PatientResponseDto> response = doctorFacade.getPatients(webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);*/

        /*model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/doctor/my_patients");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "My Patients");*/
        return "pages/doctor/my_patients";
    }

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
