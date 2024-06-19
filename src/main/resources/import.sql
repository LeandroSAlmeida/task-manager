INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Leandro', 'test', 'contact.dev.leandro@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_task (title,description, created_At, finished_At, status, priority, user_id) VALUES ('Sample Task 1','This is a description for task 1.', '2024-06-09T10:00:00', '2024-06-10T15:00:00', 0, 2, 2);
INSERT INTO TB_TASK (title, description, created_At, finished_At, status, priority, user_id) VALUES ('Sample Task 2', 'This is a description for task 2.', '2024-06-09T10:00:00', '2024-06-10T15:00:00', 0, 2, 2);
INSERT INTO tb_task (title,description, created_At, finished_At, status, priority, user_id) VALUES ('Sample Task 3','This is a description for task 3.', '2024-06-09T10:00:00', '2024-06-10T15:00:00', 0, 2, 2);
INSERT INTO TB_TASK (title, description, created_At, finished_At, status, priority, user_id) VALUES ('maria', 'This is a description for task 4.', '2024-06-09T10:00:00', '2024-06-10T15:00:00', 0, 2, 3);
INSERT INTO TB_TASK (title, description, created_At, finished_At, status, priority, user_id) VALUES ('maria', 'This is a description for task 4.', '2024-06-09T10:00:00', '2024-06-10T15:00:00', 0, 2, 3);
INSERT INTO TB_TASK (title, description, created_At, finished_At, status, priority, user_id) VALUES ('maria', 'This is a description for task 4.', '2024-06-09T10:00:00', '2024-06-10T15:00:00', 0, 2, 3);


INSERT INTO tb_role (authority) VALUES ('ROLE_USER');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);

