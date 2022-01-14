package ua.com.alevel.facade.patient;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.List;

public interface PatientFacade extends BaseFacade<PatientRequestDto, PatientResponseDto> {

/*    List<PatientResponseDto> getDoctors(Long id);

    void addDoctor(Long doctorId, Long patientId);

    void removeDoctor(Long doctorId, Long patientId);*/

    List<PatientResponseDto> findAll();

    PatientUser getPatientUser(Long id);
}