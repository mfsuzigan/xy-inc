FROM maven:latest
MAINTAINER Michel F. Suzigan
COPY ../ /var/tmp/poi
WORKDIR /var/tmp/poi
ENTRYPOINT mvn spring-boot:run
EXPOSE 8080 8080