FROM openjdk:24-slim
LABEL maintainer="sthobue@glh.de"
WORKDIR /usr/src/app

ENV version="1.21.1"

RUN mkdir resources
COPY "/versions/MCSVDownloader-0.1.jar" ./
COPY "/versions/resources/versions.json" ./resources

RUN touch eula.txt
RUN echo "eula=true" >> eula.txt

CMD java -jar MCSVDownloader-0.1.jar ${version} && java -jar server.jar nogui

EXPOSE 25565