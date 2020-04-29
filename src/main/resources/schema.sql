create table users
(
    user_id  bigint         not null primary key auto_increment,
    username varchar unique not null,
    password varchar        not null
);

create table questions
(
    question_id  bigint  not null primary key auto_increment,
    text         varchar not null,
    right_answer varchar not null,
);

create table user_answers
(
    user_answer_id bigint  not null primary key auto_increment,
    question_id    bigint  not null references questions (question_id),
    user_id        bigint  not null references users (user_id),
    answer         varchar not null,
    answer_status  varchar not null
);

create unique index on user_answers (question_id, user_id);
create unique index on users (username);

insert into users
VALUES (1, 'Anton', 'anton'),
       (2, 'Kolya', 'kolya'),
       (3, 'Jenya', 'jenya');

insert into questions
VALUES (1, 'Сколько примитивных типов в Java?', '8'),
       (2, 'Является ли String immutable в Java?', 'да'),
       (3, 'Какая коллекция содержит в себе неповторяющиеся элементы?', 'set'),
       (4,
        'Какую коллекцию вы используете, если вам надо добавить 100 элементов в начало списка,ArrayList или LinkedList?',
        'LinkedList'),
       (5,
        'Как называется принцип ООП, позволяющий создавать новые классы на основе уже существующих,с частично или полностью заимствованной функциональностью?',
        'наследование');

insert into user_answers
VALUES (1, 1, 1, '5', 'WRONG'),
       (2, 2, 1, 'да', 'RIGHT'),
       (3, 3, 1, 'map', 'WRONG'),
       (4, 4, 1, 'ArrayList', 'WRONG'),
       (5, 5, 1, 'полиморфизм', 'WRONG'),
       (6, 1, 2, '7', 'WRONG'),
       (7, 2, 2, 'да', 'RIGHT'),
       (8, 3, 2, 'set', 'RIGHT'),
       (9, 4, 2, 'LinkedList', 'RIGHT'),
       (10, 5, 2, 'наследование', 'RIGHT'),
       (11, 1, 3, '8', 'RIGHT'),
       (12, 2, 3, 'нет', 'WRONG'),
       (13, 3, 3, 'set', 'WRONG'),
       (14, 4, 3, 'ArrayList', 'WRONG'),
       (15, 5, 3, 'наследование', 'RIGHT');

