INSERT INTO tb_user (email, name, password)
values ('pedro@teste.com', 'Pedro Ulhoa', '$2a$12$N7u/Z/DIqz.Q1L5thksPEusvI7nnVoe8tTtZwgkIOTFB.4Y6Dpjxa');

INSERT INTO tb_user (email, name, password)
values ('maria@teste.com', 'Maria Silva', '$2a$12$N7u/Z/DIqz.Q1L5thksPEusvI7nnVoe8tTtZwgkIOTFB.4Y6Dpjxa');

INSERT INTO tb_role(role_name)
values ('EMPLOYEE');
INSERT INTO tb_role(role_name)
values ('MANAGER');
INSERT INTO tb_role(role_name)
values ('ADMIN');

INSERT INTO user_role(user_id, role_id)
values (1, 1);

INSERT INTO user_role(user_id, role_id)
values (2, 2);