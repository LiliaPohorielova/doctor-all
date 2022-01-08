package ua.com.alevel.service.impl;

import net.bytebuddy.asm.Advice;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.config.security.DefaultUserDetailsService;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.InvalidNameOrPasswordException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.repository.user.PatientUserRepository;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.service.PatientUserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientUserServiceImpl implements PatientUserService {
//public class PatientUserServiceImpl extends DefaultUserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PatientUserRepository patientUserRepository;
    private final CrudRepositoryHelper<PatientUser, PatientUserRepository> crudRepositoryHelper;

    public PatientUserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            PatientUserRepository patientUserRepository, CrudRepositoryHelper<PatientUser, PatientUserRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.patientUserRepository = patientUserRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    public PatientUser findByEmail(String email){
        return patientUserRepository.findByEmail(email);
    }

    /*public void save(PatientUser patient) {
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        patient.setRoleType(RoleType.ROLE_PATIENT);
        patientUserRepository.save(patient);
    }

    public void isRegistered(String email) {
        PatientUser user = patientUserRepository.findByEmail(email);
        if (user != null) {
            throw new InvalidNameOrPasswordException("Somebody with that email is already registered.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws InvalidNameOrPasswordException {

        PatientUser user = patientUserRepository.findByEmail(email);

        if (user == null){
            throw new InvalidNameOrPasswordException("Invalid email or password.");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoleType().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(PatientUser patient) {
        if (patientUserRepository.existsByEmail(patient.getEmail())) {
            throw new EntityExistException("this patient is exist");
        }
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        crudRepositoryHelper.create(patientUserRepository, patient);
    }*/

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(PatientUser patient) {
        if (patientUserRepository.existsByEmail(patient.getEmail())) {
            throw new EntityExistException("this patient is exist");
        }
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        crudRepositoryHelper.create(patientUserRepository, patient);
    }

    @Override
    public void update(PatientUser entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<PatientUser> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<PatientUser> findAll(DataTableRequest request) {
        return null;
    }
}
