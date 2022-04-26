FROM openjdk:18-buster
VOLUME /tmp

EXPOSE 8080/tcp

RUN apt-get -y update
RUN apt-get -y install git
RUN git clone https://github.com/shenderov/casino.git
WORKDIR casino
RUN ./gradlew build

ENTRYPOINT ["java","-jar","/casino/build/libs/casino-0.0.1-SNAPSHOT.war"]
