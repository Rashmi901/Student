DROP table Students if exists;
CREATE TABLE Students (
    id BIGINT IDENTITY NOT NULL,
    name VARCHAR(50) NOT NULL,
    subject VARCHAR(5) NOT NULL,
    university VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);
insert into Students (name, subject, university) values ('Rashmi', 'BE', 'VTU');
insert into Students (name, subject, university) values ('Bhuvik', 'MBA', 'BU');
insert into Students (name, subject, university) values ('Srini', 'MCA', 'SVU');
