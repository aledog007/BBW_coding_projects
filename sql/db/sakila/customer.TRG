TYPE=TRIGGERS
triggers='CREATE DEFINER=`root`@`localhost` TRIGGER customer_create_date BEFORE INSERT ON customer\n	FOR EACH ROW SET NEW.create_date = NOW()'
sql_modes=1574961152
definers='root@localhost'
client_cs_names='utf8mb3'
connection_cl_names='utf8mb3_general_ci'
db_cl_names='utf8mb4_general_ci'
created=1709113502622955
