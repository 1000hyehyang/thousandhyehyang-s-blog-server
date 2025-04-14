# Step 1: 빌드 이미지
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

# gradle wrapper 포함 전체 프로젝트 복사
COPY . .

# gradlew에 실행 권한 부여
RUN chmod +x gradlew

# 의존성 먼저 다운로드 후 빌드
RUN ./gradlew build -x test

# Step 2: 실행 이미지
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "/app/app.jar"]
