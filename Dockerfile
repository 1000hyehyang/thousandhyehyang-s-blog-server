# Step 1: 빌드 단계
FROM gradle:8.4.0-jdk17 AS builder

WORKDIR /app

# 종속성 캐싱을 위해 설정 파일 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build -x test || return 0  # 의존성 미리 다운 (에러 무시)

# 소스 코드 전체 복사 및 빌드
COPY . .
RUN gradle clean build -x test

# Step 2: 실행 단계 (더 가벼운 이미지 사용)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# Render는 환경변수로 포트를 동적으로 전달함
ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]
