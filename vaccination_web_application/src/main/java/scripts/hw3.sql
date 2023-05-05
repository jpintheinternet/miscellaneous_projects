drop table if exists patients;
drop table if exists vaccines;

create table vaccines (
    vaccine_id          integer auto_increment primary key,
    vaccine_name        varchar(255) unique not null,
    doses_required      integer,
    days_between_doses  integer,
    doses_received      integer,
    doses_left          integer
);

insert into vaccines values (1, 'Pfizer/BioNTech', 2, 21, 10000, 1000);
insert into vaccines values (2, 'Johnson & Johnson', 1, null, 5000, 5000);
insert into vaccines values (3, 'Moderna', 2, 28, 10000, 10000);

create table patients (
    patient_id          integer auto_increment primary key,
    patient_name        varchar(255) not null,
    vaccine_id          integer not null references vaccines(id),
    first_dose_date     date,
    second_dose_date    date
);

insert into patients values (1, 'John Doe', 1, '2021-02-18', '2021-03-11');
insert into patients values (2, 'Jane Doe', 1, '2021-02-18', null);
insert into patients values (3, 'Tom Smith', 2, '2021-03-12', null);
insert into patients values (4, 'Jim Lee', 3, '2021-03-12', null);
