package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ua.com.alevel.config.ElasticsearchConfig;
import ua.com.alevel.config.HibernateConfig;
import ua.com.alevel.config.ScheduledConfig;
import ua.com.alevel.config.WebSecurityConfig;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.service.department.DepartmentService;

import static ua.com.alevel.GenerationUtil.NAME_OF_DEPARTMENT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {ElasticsearchConfig.class, HibernateConfig.class, ScheduledConfig.class, WebSecurityConfig.class})
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    private final static int COUNT_OF_DEPS = 5;

    @BeforeAll
    void init() {
        for (int i = 0; i < COUNT_OF_DEPS; i++) {
            DoctorsDepartment department = GenerationUtil.generateDepartment(NAME_OF_DEPARTMENT);
            departmentService.create(department);
        }

        Assertions.assertEquals(COUNT_OF_DEPS, departmentService.findAll().size());
    }

    @Order(1)
    @Test
    void shouldBeCreateDepartmentWhenAllFieldsIsNotEmpty() {
        DoctorsDepartment department = GenerationUtil.generateDepartment(NAME_OF_DEPARTMENT + "test");
        departmentService.create(department);
        Assertions.assertEquals(COUNT_OF_DEPS + 1, departmentService.findAll().size());
    }

    @Order(2)
    @Test
    void departmentNameIsNull() {
        DoctorsDepartment department = GenerationUtil.generateDepartment(null);
        departmentService.create(department);
        Assertions.assertEquals(COUNT_OF_DEPS + 2, departmentService.findAll().size());
    }

    @Order(3)
    @Test
    void shouldBeDeleteDepartment() {
        Long id = departmentService.findAll().stream().findFirst().get().getId();
        departmentService.delete(id);
        Assertions.assertEquals(COUNT_OF_DEPS + 1, departmentService.findAll().size());
    }

    @Order(4)
    @Test
    void shouldBeUpdateDepartment() {
        DoctorsDepartment department = departmentService.findAll().stream().findFirst().get();
        department.setDepartmentName("Testing");
        departmentService.update(department);
        Assertions.assertEquals("Testing", departmentService.findById(department.getId()).get().getDepartmentName());
    }

    @Order(5)
    @Test
    void shouldBeDeleteAllDepartments() {
        departmentService.findAll().forEach(department -> departmentService.delete(department.getId()));
        Assertions.assertEquals(0, departmentService.findAll().size());
    }
}

