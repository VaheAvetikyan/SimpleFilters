FROM eclipse-temurin:17-jdk-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
COPY . .
RUN ./gradlew build

FROM eclipse-temurin:17-jdk-alpine
ENV ARTIFACT_NAME=SimpleFilters-1.0-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME ./app.jar
CMD ["java","-jar","app.jar"]
