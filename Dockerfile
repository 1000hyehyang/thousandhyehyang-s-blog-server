# Step 1: Gradle 빌드 이미지
FROM gradle:8.4-jdk17 AS builder

WORKDIR /app

# 빌드 캐시 최적화를 위해 순서대로 복사
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle
COPY src ./src

# 의존성 먼저 다운로드
RUN ./gradlew build -x test

# Step 2: 실행 이미지
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

# Render는 PORT를 환경변수로 넘김
ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "/app/app.jar"]
