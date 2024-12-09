FROM tomcat:10.1.33-jre21-temurin-noble

ARG APP_NAME=application-1.0-SNAPSHOT

COPY application/build/libs/${APP_NAME}.war /usr/local/tomcat/webapps/ROOT.war