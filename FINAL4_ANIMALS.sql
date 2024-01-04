/*
7. В подключенном MySQL репозитории создать базу данных “Друзья человека”
8. Создать таблицы с иерархией из диаграммы в БД
9. Заполнить низкоуровневые таблицы именами(животных), командами которые они выполняют и датами рождения
*/

DROP SCHEMA IF EXISTS human_friends;
CREATE SCHEMA human_friends ;

USE human_friends;

-- ========================================

DROP TABLE IF EXISTS animal_types;
CREATE TABLE animal_types (
	id INT NOT NULL UNIQUE PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO animal_types (id, type_name) VALUES 
(1, 'домашние животные'),
(2, 'вьючные животные');

-- ========================================

DROP TABLE IF EXISTS commands;
CREATE TABLE commands (
	id INT NOT NULL UNIQUE PRIMARY KEY,
    cmd_name VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO commands (id, cmd_name) VALUES
(0, 'иди есть!'),
(1, 'ко мне!'),
(2, 'сидеть!'),
(3, 'фу!'),
(4, 'вперёд!'),
(5, 'отдай!'),
(6, 'брысь!'),
(7, 'НО!');

-- ========================================

DROP TABLE IF EXISTS animal_classes;
CREATE TABLE animal_classes (
	id SERIAL PRIMARY KEY, -- SERIAL = BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE
    type_id INT NOT NULL,
    animal_class VARCHAR(50) UNIQUE NOT NULL,
	FOREIGN KEY (type_id) REFERENCES animal_types(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO animal_classes (type_id, animal_class) VALUES 
(1, 'кошки'),
(1, 'собаки'),
(1, 'хомячки'),
(2, 'лошади'),
(2, 'верблюды'),
(2, 'ослы');

-- ========================================

DROP TABLE IF EXISTS animals;
CREATE TABLE animals (
	id SERIAL PRIMARY KEY, -- SERIAL = BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE
    class_id BIGINT UNSIGNED NOT NULL,
    anim_name VARCHAR(50) UNIQUE NOT NULL,
    gender CHAR(1) NOT NULL,
    birthday DATE NOT NULL,
    FOREIGN KEY (class_id) REFERENCES animal_classes(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO animals (class_id, anim_name, gender, birthday) VALUES 
(1, 'Мурка', 'f', '2022.05.24'), 
(1, 'Матроскин', 'm', '2017.11.05'),
(1, 'Гарфилд', 'm', '2014.01.04'),
(2, 'Рекс', 'm', '2018.04.21'),
(2, 'Лесси', 'f', '2020.05.01'),
(2, 'Белый Бим', 'm', '2023.07.21'),
(3, 'Ами', 'm', '2023.08.14'),
(3, 'Бонни', 'm', '2021.02.19'),
(3, 'Бусинка', 'f', '2023.05.24'),
(4, 'Буран', 'm', '2012.08.24'),
(4, 'Миледи', 'f', '2022.10.13'),
(5, 'Семёныч', 'm', '2011.05.02'),
(6, 'Морковка', 'f', '2016.07.29'),
(6, 'Осёл', 'm', '2021.12.24');

-- ========================================

DROP TABLE IF EXISTS animal_commands;
CREATE TABLE animal_commands (
    animal_id BIGINT UNSIGNED NOT NULL,	
    command_id INT NOT NULL,
	FOREIGN KEY (animal_id) REFERENCES animals(id) ON UPDATE CASCADE ON DELETE CASCADE,	
    FOREIGN KEY (command_id) REFERENCES commands(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO animal_commands (animal_id, command_id) VALUES 
(1, 0), (1, 6), (2, 6), (3, 3), (3, 0), (3, 6),
(4, 1), (4, 2), (4, 3), (5, 3), (5, 0), (5, 5), (6, 0), (6, 3),
(8, 0), (8, 6),
(10, 0), (10, 1), (10, 4), (10, 6), (10, 7), (11, 4), (11, 7),
(12, 0), (12, 1), (12, 4), (12, 7), (13, 4), (13, 7);

-- ========================================

SELECT * FROM animals;

-- 10. Удалить из таблицы верблюдов, т.к. верблюдов решили перевезти в другой питомник на зимовку. 

DELETE animals FROM animals JOIN animal_classes ON animals.class_id = animal_classes.id
	WHERE animal_class = 'верблюды';
    
SELECT * FROM animals;
    
-- Объединить таблицы лошади, и ослы в одну таблицу.

DROP TABLE IF EXISTS horses_n_donkeys;
CREATE TABLE horses_n_donkeys 
	SELECT A.* FROM animals AS A JOIN animal_classes AS AC ON A.class_id = AC.id 
		WHERE AC.animal_class = 'лошади' OR AC.animal_class = 'ослы';
        
SELECT * FROM horses_n_donkeys;

-- 11.Создать новую таблицу “молодые животные” в которую попадут все животные старше 1 года, 
--    но младше 3 лет и в отдельном столбце с точностью до месяца подсчитать возраст животных в новой таблице

DROP TABLE IF EXISTS young_animals;
CREATE TABLE young_animals 
	SELECT *, CONCAT(
		TIMESTAMPDIFF(YEAR, birthday, NOW()), ' years ', 
		MOD(TIMESTAMPDIFF(MONTH, birthday, NOW()), 12), ' months') AS age 
	FROM animals
		WHERE TIMESTAMPDIFF(MONTH, birthday, NOW()) >= 12 AND TIMESTAMPDIFF(MONTH, birthday, NOW()) <= 36;

SELECT * FROM young_animals;

-- 12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на прошлую принадлежность к старым таблицам.

SELECT AnT.*, AC.*, A.*, ACmd.*, C.* 
    FROM  
		animal_types AS AnT JOIN animal_classes AS AC ON AnT.id = AC.type_id 
			JOIN animals AS A ON A.class_id = AC.id
				LEFT JOIN animal_commands AS ACmd ON ACmd.animal_id = A.id
					LEFT JOIN commands AS C ON C.id = ACmd.command_id;





