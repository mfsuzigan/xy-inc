FROM mysql:latest
MAINTAINER Michel F. Suzigan
COPY ./src/main/resources/sql/ddl_poi.sql /docker-entrypoint-initdb.d
COPY ./src/main/resources/sql/mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf
ENV MYSQL_ROOT_PASSWORD *123!
EXPOSE 3306 3306
