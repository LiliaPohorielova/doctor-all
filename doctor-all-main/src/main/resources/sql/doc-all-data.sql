USE clinic_doctor_all;
--
-- Dumping data for table `users`
--

INSERT INTO `users` VALUES ('DOCTOR',1,'2022-01-12 17:43:30.910000','2022-01-12 17:43:30.910000','doctor777@mail.com',_binary '','$2a$10$D00GyJ6uZ/No9P4UvSbrr.oumwPXw2k6JP0UE0kslTC3zn8qINbAu','ROLE_DOCTOR'),
                           ('DOCTOR',2,'2022-01-12 17:46:01.964000','2022-01-12 17:46:01.964000','doctor1@mail.com',_binary '','$2a$10$59WM85pmZLA7dROACUVytuK/Hq6fpBBXm9/xYnBcFptNmfHkTmpLG','ROLE_DOCTOR'),
                           ('DOCTOR',4,'2022-01-12 17:57:07.874000','2022-01-12 17:57:07.874000','doctorerw@mail.com',_binary '','$2a$10$V5gU0dU/sIFc5Acxi/xjQuwb.htkXEsRsTZ426nXLZV2e0x9ljlra','ROLE_DOCTOR'),
                           ('DOCTOR',5,'2022-01-12 18:02:48.432000','2022-01-12 18:02:48.432000','doctorew1@mail.com',_binary '','$2a$10$dDWtvMULeDlYGrUlcSMC.e2Fb6C7Ab0bQW0S.uEysBKYAfRQRJ1T6','ROLE_DOCTOR'),
                           ('DOCTOR',6,'2022-01-12 18:16:45.930000','2022-01-12 18:16:45.930000','doctor111@mail.com',_binary '','$2a$10$lXIsXLg8OEzeckUPKc1XZet0GBxprPW2DAXZHH9RfcR/0BHesY2Aq','ROLE_DOCTOR'),
                           ('PATIENT',7,'2022-01-12 18:18:04.223000','2022-01-12 18:18:04.223000','pa1213@mail.com',_binary '','$2a$10$K0GcJnT.qnP0n8wpzqAtRugMNP1kfSF3zYoM9CV194yEEY0ltVQIi','ROLE_PATIENT'),
                           ('ADMIN',8,'2022-01-13 11:16:53.660000','2022-01-13 11:16:53.660000','admin@mail.com',_binary '','$2a$10$oMfrQffFQEBrb68yKooBPOoQx9fvz3/AunqTBqkPfn8Dx0ieN9I0K','ROLE_ADMIN'),
                           ('DOCTOR',9,'2022-01-13 14:56:25.695000','2022-01-13 14:56:25.695000','doctor_olesya@mail.com',_binary '','$2a$10$isCq90HugOn8GVVAkAR8ou3ImoleawBRNeiWTfNauxdc2hXAyH0Wa','ROLE_DOCTOR'),
                           ('PATIENT',10,'2022-01-13 14:58:18.926000','2022-01-13 14:58:18.926000','pat_amina@mail.com',_binary '','$2a$10$ktjpVZ2ekfy3Amt8fux.uerjcEVuyhKNfrdzExPyDyZGCZhanRZdm','ROLE_PATIENT'),
                           ('DOCTOR',11,'2022-01-13 17:58:19.842000','2022-01-13 17:58:19.842000','doctor@mail.com',_binary '','$2a$10$OSfyH7jkCC7KyttgA/MCv.s9/UWaEnbuo.UIPjWZ.AdfHPMp.sMjy','ROLE_DOCTOR'),
                           ('DOCTOR',14,'2022-01-13 18:37:54.590000','2022-01-13 18:37:54.590000','doctortest@mail.com',_binary '','$2a$10$QOZ5JKDVnjXXQ5o2Zwg.xeerFtVN5yRatzKQeAIoAQGwwILdVmcEK','ROLE_DOCTOR'),
                           ('PATIENT',20,'2022-01-15 00:18:49.337000','2022-01-15 00:18:49.337000','patient@mail.com',_binary '','$2a$10$VprdO2ytrfu7W4Hre5Ct4.Ur5PiNtZiBc50TOakqN0vjWrnJ31noq','ROLE_PATIENT');

--
-- Dumping data for table `doc_departments`
--

INSERT INTO `doc_departments` VALUES (1,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','https://technext.github.io/docmed/img/department/3.png','Dental Care','Dental Care Department'),
                                     (2,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','https://technext.github.io/docmed/img/department/2.png','Surgery','Surgery Department'),
                                     (3,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','https://i.ibb.co/CtMkqM0/What-Types-of-Cardiologists-Are-There.jpg','Cardiology','Cardiology Department'),
                                     (4,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','https://i.ibb.co/XY3CgsZ/Close-up-Doctor-in-Blue-Uniform-Comfforting-Leg-of-Sitting-Young-Woman-in-Doctor-Office-Traumatology.jpg','Orthopedics','Orthopedics Department'),
                                     (5,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','https://i.ibb.co/bXb4cTs/ter.jpg','Therapeutic','Department of therapy'),
                                     (6,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','https://i.ibb.co/7Qw4ZnT/A-senior-woman-of-african-descent-listens-to-her-doctor-explain-the-outcome-of-her-most-recent-tests.jpg','Psychology','Depatment Of Psyhology');

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` VALUES (1,'2022-01-12 18:02:48.539000','2022-01-13 17:17:18.155000','Surgeon',NULL,'Surgeon','Surgeon','SURGEON',2,1),
                             (2,'2022-01-12 18:02:48.539000','2022-01-13 17:17:18.155000','Cardi',NULL,'Cardi','Cardi','CARDIOLOGIST',3,2),
                             (3,'2022-01-12 18:02:48.539000','2022-01-13 17:17:18.155000','Ortho',NULL,'Ortho','Ortho','ORTHOPEDIST',4,4),
                             (4,'2022-01-12 18:02:48.539000','2022-01-13 17:17:18.155000','Andrew','https://symbiosis.ae/images/doctors/Dr_Ateeque1.png','Kovalenko','Sergeevich','SURGEON',2,5),
                             (5,'2022-01-12 18:16:46.030000','2022-01-13 17:15:26.130000','Oleg','https://thumbs.dreamstime.com/b/doctor-seated-hands-together-20102822.jpg','Demchenko','Ivanovich','DENTIST',1,6),
                             (6,'2022-01-13 14:56:25.787000','2022-01-13 14:56:25.787000','Olesya','https://jiklom.ukit.me/templates/new_medicine/img/full_lpTdrZAj.jpg','Pogorelova','Andriivna','PHYSICIAN',6,9),
                             (7,'2022-01-13 17:58:19.932000','2022-01-13 17:58:19.932000','Doctor','https://media.istockphoto.com/photos/vertical-portrait-of-charming-smiling-young-female-doctor-in-uniform-picture-id941488900?k=20&m=941488900&s=170667a&w=0&h=5s_CvK3O_C1VOMeaTPUvxYedAkT30YHGRxLnJ7wL3qQ=','Doctor','Doctor','DENTIST',1,11);

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` VALUES (2,'2022-01-12 18:18:04.303000','2022-01-12 18:18:04.303000','2022-01-05','Vasya','MALE',NULL,'Petrov','380989391922',7),
                              (3,'2022-01-13 14:58:19.005000','2022-01-13 14:58:19.005000','2011-04-22','Amina','FEMALE',NULL,'Pogorelova','380989391922',10),
                              (6,'2022-01-15 00:18:49.412000','2022-01-15 00:18:49.412000','2022-01-28','Petya','MALE',NULL,'Ivanov','380989391922',20);

--
-- Dumping data for table `declaration`
--

INSERT INTO `declaration` VALUES (7,3),(6,6),(7,6);


--
-- Dumping data for table `slots`
--

INSERT INTO `slots` VALUES (18,'2022-01-21 00:05:50.229000','2022-01-23 18:17:55.269000','2022-01-27','11:00:00',2,7),
                           (19,'2022-01-21 01:01:14.615000','2022-01-23 21:30:40.821000','2022-01-21','07:30:00',2,7),
                           (20,'2022-01-21 01:56:50.505000','2022-01-23 18:18:18.155000','2022-01-04','12:00:00',2,7),
                           (22,'2022-01-21 02:28:07.615000','2022-01-23 21:26:33.043000','2022-01-21','13:00:00',2,7),
                           (23,'2022-01-21 02:45:06.885000','2022-01-23 21:29:50.431000','2022-01-21','12:00:00',3,7),
                           (24,'2022-01-23 19:30:47.199000','2022-01-23 21:29:56.705000','2022-01-23','12:00:00',3,7),
                           (29,'2022-01-23 23:43:47.361000','2022-01-24 01:23:05.062000','2022-01-26','23:00:00',2,7),
                           (30,'2022-01-23 23:47:42.435000','2022-01-26 13:02:11.746000','2022-01-26','10:00:00',3,7),
                           (31,'2022-01-24 01:39:35.285000','2022-01-24 01:40:22.601000','2022-01-24','12:00:00',3,7),
                           (32,'2022-01-24 01:39:57.895000','2022-01-24 23:00:36.391000','2022-01-24','23:00:00',3,7),
                           (33,'2022-01-24 01:46:44.231000','2022-01-24 14:19:27.810000','2022-01-24','23:00:00',3,7),
                           (34,'2022-01-24 02:15:51.108000','2022-01-24 02:16:05.787000','2022-01-27','23:00:00',3,7),
                           (35,'2022-01-24 02:17:01.737000','2022-01-25 13:46:00.610000','2022-01-25','12:00:00',3,7),
                           (36,'2022-01-24 02:25:07.907000','2022-01-24 14:20:06.373000','2022-01-27','11:00:00',1,7),
                           (37,'2022-01-24 12:57:19.914000','2022-01-24 12:57:19.914000','2022-01-27','22:00:00',0,7),
                           (38,'2022-01-24 14:26:27.031000','2022-01-24 14:26:27.031000','2022-01-27','11:59:00',0,7),
                           (41,'2022-01-24 14:52:52.329000','2022-01-24 14:52:52.329000','2022-01-27','00:53:00',0,7);


--
-- Dumping data for table `patient_appointments`
--

INSERT INTO `patient_appointments` VALUES (14,'2022-01-21 00:06:09.272000','2022-01-21 00:06:09.272000',6,18),
                                          (15,'2022-01-21 01:35:42.461000','2022-01-21 01:35:42.461000',6,19),
                                          (16,'2022-01-21 01:57:26.635000','2022-01-21 01:57:26.635000',6,20),
                                          (17,'2022-01-23 18:17:04.969000','2022-01-23 18:17:04.969000',6,23),
                                          (18,'2022-01-23 19:28:15.366000','2022-01-23 19:28:15.366000',6,22),
                                          (19,'2022-01-23 20:41:03.718000','2022-01-23 20:41:03.718000',6,24),
                                          (23,'2022-01-24 01:21:42.618000','2022-01-24 01:21:42.618000',3,29),
                                          (24,'2022-01-24 01:21:59.722000','2022-01-24 01:21:59.722000',6,30),
                                          (25,'2022-01-24 01:40:08.212000','2022-01-24 01:40:08.212000',6,31),
                                          (26,'2022-01-24 01:41:27.810000','2022-01-24 01:41:27.810000',6,32),
                                          (27,'2022-01-24 01:46:57.180000','2022-01-24 01:46:57.180000',3,33),
                                          (28,'2022-01-24 02:15:58.149000','2022-01-24 02:15:58.149000',6,34),
                                          (29,'2022-01-24 02:17:10.715000','2022-01-24 02:17:10.715000',6,35),
                                          (30,'2022-01-24 14:20:06.378000','2022-01-24 14:20:06.378000',6,36);

--
-- Dumping data for table `vaccinations`
--

INSERT INTO `vaccinations` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                                  (2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
                                  (3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)

