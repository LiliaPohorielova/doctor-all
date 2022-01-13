package ua.com.alevel.facade.doctor;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;

import java.util.List;

public interface DoctorFacade extends BaseFacade<DoctorRequestDto, DoctorResponseDto> {

/*    List<PatientResponseDto> getPatients(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);*/

    List<DoctorResponseDto> findAll();

    DoctorUser getDoctorUser(Long id);
}