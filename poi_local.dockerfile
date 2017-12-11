FROM maven:latest
MAINTAINER Michel F. Suzigan
COPY ./target/poi*jar /var/tmp/poi/poi.jar
WORKDIR /var/tmp/poi
ENTRYPOINT java -DdbUrl="jdbc:mysql://mysql:3306/poi?autoReconnect=true&useSSL=false" -jar poi.jar
