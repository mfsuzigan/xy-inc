FROM mysql:latest
MAINTAINER Michel F. Suzigan
COPY ./sql/poi_db.sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD *123!
EXPOSE 3306 3306