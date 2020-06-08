FROM openjdk:8-jdk-alpine
MAINTAINER alvaronascimentoapn 
RUN apk update && apk upgrade
RUN mkdir -p /usr/local/dockerize

ADD  @project.build.finalName@.jar /usr/local/dockerize/
ADD run.sh run.sh

RUN chmod +x run.sh

CMD ./run.sh

