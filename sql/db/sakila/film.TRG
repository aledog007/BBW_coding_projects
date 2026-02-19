TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER `ins_film` AFTER INSERT ON `film` FOR EACH ROW BEGIN\n    INSERT INTO film_text (film_id, title, description)\n        VALUES (new.film_id, new.title, new.description);\n  END' 'CREATE DEFINER=`root`@`localhost` TRIGGER `upd_film` AFTER UPDATE ON `film` FOR EACH ROW BEGIN\n    IF (old.title != new.title) OR (old.description != new.description) OR (old.film_id != new.film_id)\n    THEN\n        UPDATE film_text\n            SET title=new.title,\n                description=new.description,\n                film_id=new.film_id\n        WHERE film_id=old.film_id;\n    END IF;\n  END' 'CREATE DEFINER=`root`@`localhost` TRIGGER `del_film` AFTER DELETE ON `film` FOR EACH ROW BEGIN\n    DELETE FROM film_text WHERE film_id = old.film_id;\n  END'
sql_modes=1574961152 1574961152 1574961152
definers='root@localhost' 'root@localhost' 'root@localhost'
client_cs_names='utf8mb3' 'utf8mb3' 'utf8mb3'
connection_cl_names='utf8mb3_general_ci' 'utf8mb3_general_ci' 'utf8mb3_general_ci'
db_cl_names='utf8mb4_general_ci' 'utf8mb4_general_ci' 'utf8mb4_general_ci'
created=1709113490673858 1709113490690527 1709113490719084
