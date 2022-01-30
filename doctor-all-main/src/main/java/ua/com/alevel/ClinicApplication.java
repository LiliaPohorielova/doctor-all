package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import ua.com.alevel.elastic.index.DoctorIndex;
import ua.com.alevel.elastic.index.PatientIndex;

import javax.annotation.PreDestroy;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class ClinicApplication {

    private final ElasticsearchOperations elasticsearchOperations;

    public ClinicApplication(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClinicApplication.class, args);
    }

    @PreDestroy
    public void deleteIndex() {
        elasticsearchOperations.indexOps(DoctorIndex.class).delete();
        elasticsearchOperations.indexOps(PatientIndex.class).delete();
    }
}
