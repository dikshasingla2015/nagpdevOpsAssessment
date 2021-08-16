FROM openjdk:8-jdk-alpine
ADD ./target/inventory-management.jar inventory.jar

EXPOSE 3515
ENTRYPOINT [ "java","-jar","/inventory.jar" ]