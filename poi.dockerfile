FROM maven:latest
MAINTAINER Michel F. Suzigan
COPY ./target/poi*jar /var/tmp/poi/poi.jar
WORKDIR /var/tmp/poi
ENTRYPOINT java -DdbUrl="jdbc:mysql://177.153.20.102:11376/poi?autoReconnect=true&useSSL=false" -jar poi.jar
EXPOSE 8080 8080
