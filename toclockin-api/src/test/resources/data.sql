INSERT INTO tb_user (email, name, password)
values ('pedro@teste.com', 'Pedro Ulhoa', '$2a$12$N7u/Z/DIqz.Q1L5thksPEusvI7nnVoe8tTtZwgkIOTFB.4Y6Dpjxa');
INSERT INTO tb_time_tracking_record(time_tracking_date, time_tracking_type, user_message, user_id)
values ('2021-10-30 10:44:05', 'ENTRY', 'Ponto de entrada', 1);

INSERT INTO tb_role(role_name)
values ('EMPLOYEE');
INSERT INTO tb_role(role_name)
values ('MANAGER');
INSERT INTO tb_role(role_name)
values ('ADMIN');

INSERT INTO user_role(user_id, role_id)
values (1, 1);