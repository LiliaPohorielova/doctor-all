# Для создания базы данных - запусите приложение
# Для заполнения базы данных, используйте затем этот скрипт
USE clinic_doctor_all;
insert into doc_departments values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Cardiology');
insert into doc_departments values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Orthopedic');
insert into doc_departments values (3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Neurology');
insert into doc_departments values (4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Eye Care');
insert into doc_departments values (5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Dental Care');
insert into doc_departments values (6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Family Doctors');

insert into doctors values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Mykyta', 'Kuznetsov', 'Victorovich', 'DENTIST', 5);
insert into doctors values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Ivan', 'Sidorov', 'Aleksandrovich', 'DENTIST', 5);
insert into doctors values (3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Sofia', 'Petrova', 'Vyacheslavovna', 'THERAPIST', 6);
insert into doctors values (4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Petr', 'Ivanov', 'Vasilyevich', 'SURGEON', 5);
insert into doctors values (5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Dmitrii', 'Petrenko', 'Victorovich', 'SURGEON', 4);
insert into doctors values (6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Eugeni', 'Kunaev', 'Vasilyevich', 'THERAPIST', 6);
insert into doctors values (7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Elina', 'Shatska', 'Mikhaylovna', 'PHYSICIAN', 3);
insert into doctors values (8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Stanicslav', 'Bovchaluk', 'Yaroslavovich', 'PHYSICIAN', 3);
insert into doctors values (9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Mykyta', 'Dinovich', 'Vladimirovich', 'THERAPIST', 6);
insert into doctors values (10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Arsen', 'Makashvilli', 'Timurovich', 'CARDIOLOGIST', 1);
insert into doctors values (11, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Dmitrii', 'Prakiv', 'Stepanovich', 'SURGEON', 2);
insert into doctors values (12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), true, 'Ivan', 'Divakov', 'Petrovich', 'SURGEON', 1);
/*
insert into patients
values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2002-01-05', 'Mykyta', 'Antonenko', true);
insert into patients
values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2001-02-15', 'Vadym', 'Bondarenko', true);
insert into patients
values (3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2000-03-03', 'Kateryna', 'Burtseva', true);
insert into patients
values (4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2002-01-17', 'Artem', 'Hunchenko', true);
insert into patients
values (5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2002-01-05', 'Oleksandr', 'Davydov', true);
insert into patients
values (6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2003-06-09', 'Eduard', 'Dasiv', true);
insert into patients
values (7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2003-05-06', 'Denys', 'Dovhalin', true);
insert into patients
values (8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2001-04-05', 'Iryna', 'Klen', true);
insert into patients
values (9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2001-03-15', 'Nataliia', 'Kulyk', true);
insert into patients
values (10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2000-02-14', 'Kyrylov', 'Lobano', true);
insert into patients
values (11, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2000-01-13', 'Andrii', 'Makhotka', true);
insert into patients
values (12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2001-11-12', 'Anastasiia', 'Mohylevska', true);
insert into patients
values (13, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2001-12-11', 'Yaroslav', 'Mulika', true);
insert into patients
values (14, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2002-09-10', 'Yelisei', 'Panchenko', true);
insert into patients
values (15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2003-06-17', 'Liliia', 'Pohorielova', true);
insert into patients
values (16, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2003-03-02', 'Vladyslav', 'Poliakov', true);
insert into patients
values (17, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2010-07-17', 'Daniil', 'Svistelnyk', true);
insert into patients
values (18, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2002-03-04', 'Anastasiia', 'Suvorova', true);
insert into patients
values (19, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2003-03-19', 'Daniil', 'Tverdokhlib', true);
insert into patients
values (20, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2005-02-18', 'Ihor', 'Cherednychenko', true);
insert into patients
values (21, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), '2000-01-10', 'Yehor', 'Shakirov', true);


insert into slots
values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 1);
insert into slots
values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 1);
insert into slots
values (3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 1);
insert into slots
values (4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 2);
insert into slots
values (5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 2);
insert into slots
values (6, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 2);
insert into slots
values (7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 3);
insert into slots
values (8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 3);
insert into slots
values (9, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_DATE(),
        DATE_ADD(FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(now()) / 3600) * 3600), INTERVAL 45 MINUTE), 0, 3);

insert into patient_appointments
values (1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1, 1);
insert into patient_appointments
values (2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 2, 4);
insert into patient_appointments
values (3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 3, 7);
*/