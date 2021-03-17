insert into public.users (name, last_name, email, password, role, status, dt_register)
values ('Admin', 'Admin', 'admin@rentcar.com', '$2a$12$BXmpmCXEL0odViE2Zujb5OdT5EwCdBLTf8mMN4Paarm1UIrOmDLYm', 'ADMIN', 1, '2021-03-16 15:53:09.304000');

insert into public.gama (name, description, price, status) values ('Alta', 'Carros de Gama Alta', 95.0000, 1);
insert into public.gama (name, description, price, status) values ('Media', 'Carros de Gama Media', 65.0000, 1);
insert into public.gama (name, description, price, status) values ('Baja', 'Carros de Gama Baja', 45.0000, 1);

insert into public.cars (license_plate, brand, model, type, description, id_gama, color, km, price, status) values ('AAABBB', 'Chevrolet', 'Aveo', 'Sedan', 'Aveo 2021', 3, 'Negro', 100, null, 1);
insert into public.cars (license_plate, brand, model, type, description, id_gama, color, km, price, status) values ('BBBBCCC', 'Ford', 'Focus', 'Sedan', 'Focus 2021', 2, 'Gris', 30, null, 1);
insert into public.cars (license_plate, brand, model, type, description, id_gama, color, km, price, status) values ('CCCDDD', 'Nissan', 'Pathfinder', 'Camioneta', 'Pathfinder 2021', 1, 'Rojo', 100, null, 1);
