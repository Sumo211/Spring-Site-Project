INSERT INTO xxx_user(id, email, password, role) VALUES (1, 'test-1@mail.com', 'pwd-1', 'USER');
INSERT INTO xxx_user(id, email, password, role) VALUES (2, 'test-2@mail.com', 'pwd-2', 'USER');
INSERT INTO xxx_user(id, email, password, role) VALUES (3, 'test-3@mail.com', 'pwd-3', 'USER');
INSERT INTO xxx_user(id, email, password, role) VALUES (4, 'test-4@mail.com', 'pwd-4', 'USER');
INSERT INTO xxx_user(id, email, password, role) VALUES (5, 'test-5@mail.com', 'pwd-5', 'USER');

INSERT INTO film(id, code, type, description) VALUES (1, 'ARM', 'XXX', now(), 'Desc...');

INSERT INTO rating(id, user_id, film_id, value, comment) VALUES (1, 1, 1, 1, '...');
INSERT INTO rating(id, user_id, film_id, value, comment) VALUES (2, 2, 1, 2, '...');
INSERT INTO rating(id, user_id, film_id, value, comment) VALUES (3, 3, 1, 3, '...');
INSERT INTO rating(id, user_id, film_id, value, comment) VALUES (4, 4, 1, 4, '...');
INSERT INTO rating(id, user_id, film_id, value, comment) VALUES (5, 5, 1, 5, '...');