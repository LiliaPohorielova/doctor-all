package ua.com.alevel.persistence.repository.user;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.user.DoctorUser;

@Repository
public interface DoctorUserRepository extends UserRepository<DoctorUser> { }
