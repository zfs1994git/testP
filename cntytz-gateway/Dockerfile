FROM pig4cloud/java:8-jre

MAINTAINER liucode@qq.com

ARG JAR_FILE=./target/cntytz-gateway.jar

# JVM调优参数等额外参数
ENV PARAMS ""

ENV TZ=PRC

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /tmp

EXPOSE 9999

ADD ${JAR_FILE} ./app.jar

ENTRYPOINT ["java","-jar","app.jar","-Djava.security.egd=file:/dev/./urandom","$PARAMS"]