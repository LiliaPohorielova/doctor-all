package ua.com.alevel.facade.doctor;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.util.List;
import java.util.Set;

public interface DoctorFacade extends BaseFacade<DoctorRequestDto, DoctorResponseDto> {

    Set<PatientResponseDto> getPatients(Long id);

    PageData<PatientResponseDto> getPatientsTable(Long id, WebRequest request);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);

    List<DoctorResponseDto> findAll();

    DoctorUser getDoctorUser(Long id);

    Set<SlotResponseDto> getSlots(Long id);

    void removeSlot(Long doctorId, Long slotId);
}