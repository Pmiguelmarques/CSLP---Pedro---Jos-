FROM openjdk:7

MAINTAINER Jose Frias <josefrias99@ua.pt>

COPY . ~/Desktop/java-app

WORKDIR ~/Desktop/java-app

RUN javac *.java 
USER root
RUN chmod a+x run.sh
CMD ["./run.sh"] 
