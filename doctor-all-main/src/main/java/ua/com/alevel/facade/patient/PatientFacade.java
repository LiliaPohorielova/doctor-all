package ua.com.alevel.facade.patient;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.appointment.AppointmentResponseDto;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;
import ua.com.alevel.web.dto.response.vaccination.VaccinationResponseDto;

import java.util.List;
import java.util.Set;

public interface PatientFacade extends BaseFacade<PatientRequestDto, PatientResponseDto> {

    Set<DoctorResponseDto> getDoctors(Long id);

    PageData<DoctorResponseDto> getDoctorsTable(Long id, WebRequest request);

    Set<AppointmentResponseDto> getAppointments(Long id);

    List<PatientResponseDto> findAll();

    PatientUser getPatientUser(Long id);

    void addAppointment(Long appointmentId, Long patientId);

    void addVaccination(Long vaccinationId, Long patientId);

    Set<VaccinationResponseDto> getVaccinations(Long id);

    List<PatientResponseDto> search(WebRequest webRequest);
}