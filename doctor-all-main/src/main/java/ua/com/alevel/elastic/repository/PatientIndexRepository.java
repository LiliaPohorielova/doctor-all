package ua.com.alevel.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.com.alevel.elastic.index.PatientIndex;

public interface PatientIndexRepository extends ElasticsearchRepository<PatientIndex, String> { }
