package ua.com.alevel.cron;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.alevel.elastic.index.DoctorIndex;
import ua.com.alevel.elastic.repository.DoctorIndexRepository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SyncElasticCronService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final DoctorIndexRepository doctorIndexRepository;
    private final DoctorRepository doctorRepository;

    public SyncElasticCronService(
            ElasticsearchOperations elasticsearchOperations,
            DoctorIndexRepository doctorIndexRepository,
            DoctorRepository doctorRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.doctorIndexRepository = doctorIndexRepository;
        this.doctorRepository = doctorRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void syncToSupplier() {
        elasticsearchOperations.indexOps(DoctorIndex.class).refresh();
        doctorIndexRepository.deleteAll();
        doctorIndexRepository.saveAll(prepareDataset());
    }

    private Collection<DoctorIndex> prepareDataset() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorIndex> doctorIndices = new ArrayList<>();
        doctors.forEach(doctor -> {
            DoctorIndex doctorIndex = new DoctorIndex();
            doctorIndex.setName(doctor.getLastname() + ' ' + doctor.getFirstname() + ' ' + doctor.getMiddleName());
            doctorIndices.add(doctorIndex);
        });
        return doctorIndices;
    }
}