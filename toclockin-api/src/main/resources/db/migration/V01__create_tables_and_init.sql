CREATE TABLE `tb_user`
(
    `id`       bigint(20)   NOT NULL AUTO_INCREMENT,
    `email`    varchar(80)  NOT NULL,
    `name`     varchar(100) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_EMAIL` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tb_time_tracking_record`
(
    `id`                 bigint(20)  NOT NULL AUTO_INCREMENT,
    `time_tracking_date` datetime(6) NOT NULL,
    `time_tracking_type` varchar(20) NOT NULL,
    `user_message`       varchar(255) DEFAULT NULL,
    `user_id`            bigint(20)  NOT NULL,
    PRIMARY KEY (`id`),
    KEY `KEY_USER_ID` (`user_id`),
    CONSTRAINT `FK_TIME_TRACKING_USER` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tb_role`
(
    `id`        bigint(20)  NOT NULL AUTO_INCREMENT,
    `role_name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `user_role`
(
    `user_id` bigint(20) NOT NULL,
    `role_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    KEY `FK_ROLE_ID` (`role_id`),
    CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
    CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO tb_user (email, name, password) values ('pedro@teste.com', 'Pedro Ulhoa', '$2a$12$N7u/Z/DIqz.Q1L5thksPEusvI7nnVoe8tTtZwgkIOTFB.4Y6Dpjxa');
INSERT INTO tb_time_tracking_record(time_tracking_date, time_tracking_type, user_message, user_id) values ('2021-10-30 10:44:05', 'ENTRY', 'Ponto de entrada', 1);

INSERT INTO tb_role(role_name)  values ('EMPLOYEE');
INSERT INTO tb_role(role_name)  values ('MANAGER');
INSERT INTO tb_role(role_name)  values ('ADMIN');

INSERT INTO user_role(user_id, role_id) values (1, 1);