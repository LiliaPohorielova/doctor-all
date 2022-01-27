package ua.com.alevel.facade.department;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.web.dto.request.department.DepartmentRequestDto;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.department.DepartmentResponseDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface DepartmentFacade extends BaseFacade<DepartmentRequestDto, DepartmentResponseDto> {

    List<DepartmentResponseDto> findAll();

    DoctorsDepartment findDepartmentBySpecialization(DoctorSpecialization doctorSpecialization);
}
