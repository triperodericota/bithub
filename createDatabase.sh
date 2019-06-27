#!/usr/bin/env bash
echo "Si lo considera pertinente ejecute este archivo con privilegios root

"
echo "Por favor, ingrese su contraseña de mysql para crear la base de datos: "
read -s root_pass
echo "Por favor, ingrese la IP del host donde se ejecuta su DBMS: "
read host_ip

mysql -uroot -p$root_pass -e "drop database if exists bd2_grupo20;
create database bd2_grupo20; drop user if exists 'cliente'@'$host_ip';
 create user 'cliente'@'$host_ip' identified by 'bd2';
 grant all privileges on bd2_grupo20.* TO 'cliente'@'$host_ip';"

service mongod start

echo "Se inicio el servicio de Mongo"
echo "Se le asignó el usuario 'cliente' con la contraseña 'bd2'"
