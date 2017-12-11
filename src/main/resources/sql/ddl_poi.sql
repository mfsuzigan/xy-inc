create database poi;
CREATE USER 'xy.inc'@'%' IDENTIFIED BY '*123!';
GRANT ALL PRIVILEGES ON *.* TO 'xy.inc'@'%' IDENTIFIED BY '*123!' WITH GRANT OPTION;
flush privileges;
