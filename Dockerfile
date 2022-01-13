FROM openjdk:16-alpine
WORKDIR /app/

COPY ./build/libs/argon-*-all.jar argon.jar
COPY ./scripts/run.sh run.sh

RUN chmod +x run.sh
CMD ["./run.sh"]