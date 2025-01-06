FROM openjdk:17
ADD target/szd-z000.jar /data/szd-z000.jar
ADD target/classes/bootstrap.yml /data/bootstrap.yml
EXPOSE 9090
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /data/szd-z000.jar --spring.config.location=/data/bootstrap.yml
