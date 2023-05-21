delete from airportTest.user_role;
delete from airportTest.users;

insert into airportTest.users(id, active, date_of_created, email, last_name, name, password, phone_number, second_name) values (1,true,'98-12-31 11:30:45','admin@admin.admin','admin','admin','$2a$08$SITilNXD6Bl8nMfL/N8k6ORX6aG4asbj8xzsJ6fIfSmpaA6qS2yXi','123','qwe');
insert into airportTest.user_role(user_id, roles) values (1,'ROLE_ADMIN');
