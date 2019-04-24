mysql -uroot -e "drop database if exists bd2_grupo20; create database bd2_grupo20; drop user if exists 'cliente'@'localhost'; create user 'cliente'@'localhost' identified by 'bd2'; grant all privileges on *.* TO 'cliente'@'localhost';"
 
