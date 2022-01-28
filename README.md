# Clinic DoctorAll
### Final project on course Java NIX 9
Appointment management system using Hospital as an example. Web application include such user roles:
*  Admin
*  Doctor
*  Patient

### Main functionality:

* Admin create/update/delete doctors and patients. Find all doctors by departments.
* Doctor create/update/delete slots and add patients. Find all patients and all slots by doctor.
* Patient create/update/delete appointments, add doctor, get vaccinated. Find all doctors and all appointments by patient.
* Vaccination supplier module provides availability of vaccines. 

### Main Technologies used:

* #### Spring Boot
* #### View: Thymeleaf (HTML), CSS, Bootstrap, JS
* #### Database management system: MySQL 
* #### 2 Modules: Main, Supplier

### Get Started:
1. Run Vaccination Supplier (data from supplier.sql)
2. Run Clinic Application (data from doc-all-data.sql)
3. Check init.mode = never and init = false in application.properties
4. Go to http://localhost:8080/open/dashboard/
5. Login/Register as doctor/patient or use login data.

### Login Data:
1. Admin
- admin@mail.com
- rootroot

2. Patient
- patient@mail.com
- rootroot

3. Doctor
- doctor@mail.com
- rootroot