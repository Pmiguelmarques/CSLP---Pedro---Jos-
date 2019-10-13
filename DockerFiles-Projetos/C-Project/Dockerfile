FROM ubuntu:bionic

MAINTAINER Jose Frias <josefrias99@ua.pt>

COPY . ~/Desktop/c-app

WORKDIR ~/Desktop/c-app
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && \
    apt-get -y install gcc mono-mcs && \
    rm -rf /var/lib/apt/lists/*
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y locales

RUN sed -i -e 's/# pt_PT.UTF-8 UTF-8/pt_PT.UTF-8 UTF-8/' /etc/locale.gen && \
    dpkg-reconfigure --frontend=noninteractive locales && \
    update-locale LANG=pt_PT.UTF-8

ENV LANG pt_PT.UTF-8   
RUN gcc tsp.c -o tsp -lm

CMD ["./tsp"] 
