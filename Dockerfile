FROM openjdk:17

RUN mkdir /zadatak

COPY out/production/Zadatak/ /zadatak

WORKDIR /zadatak

CMD java Main