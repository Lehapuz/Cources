create table courses
(
    id         serial primary key,
    name       varchar(255),
    duration   int,
    price      int,
    teacher_id int
);

insert into courses (id, name, duration, price, teacher_id)
values (10, 'drawing', 10, 10.1, 10),
       (20, 'reading', 5, 8.8, 20);

create table teachers
(
    id         serial primary key,
    name       varchar(255),
    age        int
);

insert into teachers (id, name, age)
values (10, 'Alex', 41),
       (20, 'Oleg', 36);

create table students
(
    id         serial primary key,
    name       varchar(255),
    age        int
);

insert into students (id, name, age)
values (10, 'Petr', 28),
       (20, 'Sasha', 29);

create table subscriptions
(
    id         serial primary key,
    student_id int,
    course_id  int
);

insert into subscriptions (id, student_id, course_id)
values (10, 20, 10),
       (20, 10, 20);