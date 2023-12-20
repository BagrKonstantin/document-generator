INSERT INTO roles (role_id, title)
VALUES
    (1, 'USER'),
    (2, 'MODERATOR'),
    (3, 'ADMINISTRATOR');

INSERT INTO document_types (document_type_id, tittle)
VALUES
    (1, 'Техническое задание'),
    (2, 'Пояснительная записка'),
    (3, 'Текст программы'),
    (4, 'Руководство оператора'),
    (5, 'Программа и методика испытаний');

INSERT INTO person (firstname, surname, lastname, status)
VALUES
    ('Ольга', 'Максименкова', 'Вениаминовна', 'Научный сотрудник'),
    ('Елена', 'Песоцкая', 'Юрьевна', 'Штатный преподаватель');

INSERT INTO faculty (title) VALUES ('Факультет Компьютерных наук');

INSERT INTO department (faculty_id, title)
VALUES
    (1, 'Программная инженерия'),
    (1, 'Прикладная математика и информатика');

INSERT INTO "user" (firstname, surname, lastname, email, username, hashed_password)
VALUES
    ('Ксения', 'Жмурина', 'Игоревна', 'KsZh11@mail.ru', 'jaklbela', 'Xhhdf6shj'),
    ('Константин', 'Багрянский', 'Дмитриевич', 'KostyaBagr@gmail.com', 'BagrKonstantin', 'hsdh77hhgs7'),
    ('Олеся', 'Кан', 'Евгеньевна', 'OlesyaKnag@mail.ru', 'Kang_Olesya', 'hag7fsgHH'),
    ('Диана', 'Кучиева', 'Сергеевна', 'DiandKuch@mail.ru', 'DancingShark', 'hg97hfRG');


INSERT INTO document (document_type_id, user_id, teacher_id, head_teacher_id, department_id, program_name, program_short_name, program_name_en, description, by_document, class_id)
VALUES
    (1, 1, 1, 1, 1, 'FPS-игра на Unity', 'FPS-игра на Unity', 'FPS-Game Made with Unity', 'Шутер от первого лица, сделанный на движке Unity', '', 1),
    (2, 1, 2, 1, 1, 'Автоматическая система тестирования для образовательных целей', 'Система тестирования', 'Automatic Testing System for Educational Purposes', 'Автоматическая система тестирования программынх работ школьников для образовательных целей', '', 1);

INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 2),
    (2, 3),
    (3, 3),
    (4, 3);