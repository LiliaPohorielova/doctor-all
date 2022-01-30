package ua.com.alevel.cron;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.alevel.elastic.index.DoctorIndex;
import ua.com.alevel.elastic.index.PatientIndex;
import ua.com.alevel.elastic.repository.DoctorIndexRepository;
import ua.com.alevel.elastic.repository.PatientIndexRepository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.persistence.repository.patient.PatientRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SyncElasticCronService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final DoctorIndexRepository doctorIndexRepository;
    private final PatientIndexRepository patientIndexRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public SyncElasticCronService(
            ElasticsearchOperations elasticsearchOperations,
            DoctorIndexRepository doctorIndexRepository,
            PatientIndexRepository patientIndexRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.doctorIndexRepository = doctorIndexRepository;
        this.patientIndexRepository = patientIndexRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void syncToSupplier() {
        elasticsearchOperations.indexOps(DoctorIndex.class).refresh();
        elasticsearchOperations.indexOps(PatientIndex.class).refresh();
        doctorIndexRepository.deleteAll();
        doctorIndexRepository.saveAll(prepareDatasetDoctor());
        patientIndexRepository.deleteAll();
        patientIndexRepository.saveAll(prepareDatasetPatient());
    }

    private Collection<DoctorIndex> prepareDatasetDoctor() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorIndex> doctorIndices = new ArrayList<>();
        doctors.forEach(doctor -> {
            DoctorIndex doctorIndex = new DoctorIndex();
            doctorIndex.setName(doctor.getLastname() + ' ' + doctor.getFirstname() + ' ' + doctor.getMiddleName());
            doctorIndices.add(doctorIndex);
        });
        return doctorIndices;
    }

    private Collection<PatientIndex> prepareDatasetPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientIndex> patientIndices = new ArrayList<>();
        patients.forEach(patient -> {
            PatientIndex patientIndex = new PatientIndex();
            patientIndex.setName(patient.getLastname() + ' ' + patient.getFirstname());
            patientIndices.add(patientIndex);
        });
        return patientIndices;
    }
}