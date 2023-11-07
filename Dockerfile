FROM ubuntu:23.10 AS dev
LABEL authors="javie"
RUN apt-get -y update \
    && apt-get -y upgrade \
    && apt-get -y install openjdk-17-jre \
    && apt-get clean
COPY target/KJTP-FinalProject-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT ["java", "-jar", "KJTP-FinalProject-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

FROM maven:3.9.5 AS prod-builder-tester
WORKDIR /user/src/mymaven
COPY . ./
CMD ["mvn", "clean", "verify"]

FROM ubuntu:23.10 AS prod
LABEL authors="javie"
RUN apt-get -y update \
    && apt-get -y upgrade \
    && apt-get -y install openjdk-17-jre \
    && apt-get clean
COPY --from=prod-builder-tester target/KJTP-FinalProject-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT ["java", "Djava.security.egd=file:/dev/./urandom", "-jar", "KJTP-FinalProject-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080