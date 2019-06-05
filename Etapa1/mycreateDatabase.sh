#!/usr/bin/env bash
mysql -uroot -ppass --protocol=tcp -e "drop database if exists bd2_grupo20; create database bd2_grupo20; drop user if exists 'cliente'@'172.17.0.1'; create user 'cliente'@'172.17.0.1' identified by 'bd2'; grant all privileges on bd2_grupo20.* TO 'cliente'@'172.17.0.1';"
