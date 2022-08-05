INSERT INTO users (id, creation, last_update, uuid, version, email, first_name, last_name, password, type)
VALUES (0, '2000-01-01 00:00:00.000000', '2000-01-01 00:00:00.000000', 'dae53315-bc2c-4c98-985f-18112ab8b373', 0,
        'root@gmail.com', 'root', 'root', '{bcrypt}$2y$10$1duHZZRfH0soSM5EA2942.JfWOcFlox9uJAadLxq5M.40rCZ1Q5g.',
        'ADMIN');

INSERT INTO admins(id)
VALUES (0);

INSERT INTO admin_permissions(admin_id, permission)
VALUES (0, 'USER_LIST_RW'),
       (0, 'USER_PROFILE_RW'),
       (0, 'VALIDATE_CERTIFICATE_W'),
       (0, 'SPECIALTY_W'),
       (0, 'SERVICE_W'),
       (0, 'QUESTIONNAIRE_RW'),
       (0, 'TECHNICAL_ISSUE_RW'),
       (0, 'FEEDBACK_RW'),
       (0, 'ROOT');