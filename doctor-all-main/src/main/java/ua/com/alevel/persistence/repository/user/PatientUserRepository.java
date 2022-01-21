package ua.com.alevel.persistence.repository.user;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.user.PatientUser;

@Repository
public interface PatientUserRepository extends UserRepository<PatientUser> { }
