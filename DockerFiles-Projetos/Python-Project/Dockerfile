FROM python:3

MAINTAINER Jose Frias <josefrias99@ua.pt>
COPY . ~/Desktop/python-app
WORKDIR ~/Desktop/python-app

#COPY requirements.txt ~/Desktop/python-app
#RUN pip install --no-cache-dir -r requirements.txt
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y locales
RUN sed -i -e 's/# pt_PT.UTF-8 UTF-8/pt_PT.UTF-8 UTF-8/' /etc/locale.gen && \
    dpkg-reconfigure --frontend=noninteractive locales && \
    update-locale LANG=pt_PT.UTF-8
ENV LANG pt_PT.UTF-8
USER root
RUN chmod -c +x run.sh


CMD ["./run.sh"]
