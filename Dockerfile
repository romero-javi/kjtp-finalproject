FROM ubuntu:23.10 AS prod
LABEL authors="javie"
RUN apt-get -y update \
    && apt-get -y upgrade \
    && apt-get -y install openjdk-17-jre \
    && apt-get clean
COPY target/KJTP-FinalProject-1.0.0.jar ./
ENTRYPOINT ["java", "-jar", "KJTP-FinalProject-1.0.0.jar"]
EXPOSE 8080