package ua.com.alevel.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.com.alevel.elastic.index.DoctorIndex;

public interface DoctorIndexRepository extends ElasticsearchRepository<DoctorIndex, String> { }
