package ua.com.alevel.web.controller.admin;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.department.DepartmentFacade;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    private final DoctorFacade doctorFacade;
    private final DepartmentFacade departmentFacade;

    public AdminController(DoctorFacade doctorFacade,
                           DepartmentFacade departmentFacade) {
        this.doctorFacade = doctorFacade;
        this.departmentFacade = departmentFacade;
    }

    private AbstractController.HeaderName[] getColumnTitles() {
        return new AbstractController.HeaderName[]{
                new AbstractController.HeaderName("#", null, null),
                new AbstractController.HeaderName("lastname", "lastname", "lastname"),
                new AbstractController.HeaderName("firstname", "firstname", "firstname"),
                new AbstractController.HeaderName("middle name", "middleName", "middleName"),
                new AbstractController.HeaderName("specialization", "specialization", "specialization"),
                new AbstractController.HeaderName("email", "doctor_user_id", "doctorUser"),
                new AbstractController.HeaderName("details", null, null),
                new AbstractController.HeaderName("edit", null, null),
                new AbstractController.HeaderName("delete", null, null)
        };
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "pages/admin/dashboard";
    }

    @GetMapping("/departments")
    public String redirectToAllDepartmentsPage(Model model) {
        model.addAttribute("departments", departmentFacade.findAll());
        return "pages/admin/departments/all-departments-cards";
    }

    @GetMapping("/departments/{id}")
    public String doctorByDepartmentId(@PathVariable Long id, Model model, WebRequest webRequest) {
        AbstractController.HeaderName[] columnTitles = getColumnTitles();
        PageData<DoctorResponseDto> response = doctorFacade.getDoctorsByDepartment(id, webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<AbstractController.HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/admin/departments/all/" + id);
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", departmentFacade.findById(id).getDepartmentName());
        return "pages/admin/departments/departments";
    }

    @PostMapping("/departments/all/{id}")
    public ModelAndView findAllDepartmentsRedirect(@PathVariable Long id, WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/admin/departments/{id}", model);
    }

    private List<AbstractController.HeaderData> getHeaderDataList(AbstractController.HeaderName[] columnTitles, PageData<DoctorResponseDto> response) {
        List<AbstractController.HeaderData> headerDataList = new ArrayList<>();
        for (AbstractController.HeaderName headerName : columnTitles) {
            AbstractController.HeaderData data = new AbstractController.HeaderData();
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
