# FROM registry.redhat.io/fuse7/fuse-java-openshift
FROM quay.io/makentenza/fuse-java-openshift

RUN mkdir /home/jboss/spring-boot
ADD spring-boot /home/jboss/spring-boot
ADD pom.xml /home/jboss/
COPY settings.xml /home/jboss/.m2/

RUN cd /home/jboss/spring-boot/sap-srfc-destination-spring-boot && \
    mvn clean install

EXPOSE 8080

CMD cd /home/jboss/spring-boot/sap-srfc-destination-spring-boot && mvn spring-boot:run
