INSERT INTO users (id, creation, last_update, uuid, version, email, first_name, last_name, password, type)
VALUES (0, '0000-00-00 00:00:00.000000', '0000-00-00 00:00:00.000000', 'dae53315-bc2c-4c98-985f-18112ab8b373', 0,
        'root@gamil.com', 'root', 'root', '{bcrypt}$2a$10$u21dQVWat7cOco75LJX6RuK12yZie8kAogyp4CAYgW.TgzFgf2rA.',
        'ADMIN');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_USER_LIST_RW');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_USER_PROFILE_RW');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_VALIDATE_CERTIFICATE_W');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_SPECIALTY_W');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_SERVICE_W');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_QUESTIONNAIRE_RW');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_TECHNICAL_ISSUE_RW');
INSERT INTO admins(id, permission)
VALUES (0, 'PERM_FEEDBACK_RW');
