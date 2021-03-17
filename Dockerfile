FROM openjdk:11
LABEL version="2.0.0" maintainer="Fabricio Leitao"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV URL_ANALISE=http://analise:9999/
ENV URL_CARTAO=http://contas:8888/
ENV DB_URL=postgres
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]