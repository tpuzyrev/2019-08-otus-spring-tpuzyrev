INSERT INTO AUTHORS (ID, `NAME`) VALUES (1, 'Николай Гоголь');
INSERT INTO AUTHORS (ID, `NAME`) VALUES (2, 'Тургенев Иван');
INSERT INTO AUTHORS (ID, `NAME`) VALUES (3, 'Чехов Антон');

INSERT INTO GENRES (ID, `NAME`) VALUES (1, 'Повесть');
INSERT INTO GENRES (ID, `NAME`) VALUES (2, 'Роман');
INSERT INTO GENRES (ID, `NAME`) VALUES (3, 'Рассказ');

INSERT INTO BOOKS (ID, `NAME`, AUTHORID, GENREID, PAGE) VALUES (1, 'Тарас Бульба', 1, 1, 300);
INSERT INTO BOOKS (ID, `NAME`, AUTHORID, GENREID, PAGE) VALUES (2, 'Отцы и дети', 2, 2, 500);
INSERT INTO BOOKS (ID, `NAME`, AUTHORID, GENREID, PAGE) VALUES (3, 'Дама с собачкой', 3, 3, 180);