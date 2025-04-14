# Step 1: Gradle wrapper로 빌드
FROM gradle:8.5.0-jdk17 AS builder
WORKDIR /app

# 캐시 최적화: gradle 관련 파일 먼저 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY build.gradle.kts .
COPY settings.gradle.kts .

# 의존성 먼저 다운
RUN ./gradlew dependencies || true

# 나머지 소스 복사 후 빌드
COPY . .
RUN ./gradlew clean build -x test

# Step 2: 실행 이미지
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# Render는 PORT 환경변수를 사용함
ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "/app/app.jar"]
