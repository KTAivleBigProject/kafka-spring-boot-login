# kafka-spring-boot
kafka 연결을 위한 java spring boot 보일러 플레이트 저장소 입니다.

# Initialize Setting
- Project: Gradle - Groovy
- Language: Java
- Spring Boot: 3.5.3
- Project Metadata:
    - Group: com.example
    - Artifact: demo
    - Name: demo
    - Description: Demo project for Spring Boot
    - Package name: com.example.demo
    - Packaging: JAR
    - Java: 21
- Dependencies:
    - Spring for Apache Kafka
    - Spring Web
    - Lombok

# 사전 준비
- docker: v28.x ('25.7.7 기준 lts)
- docker compose: v2.x ('25.7.7 기준 lts)
- java: v21.x ('25.7.7 기준 lts)

# 사용법

## 실행

```
bash ./scripts/docker-run.sh <DOCKER HUB ID> <SERVICE NAME> <SERVICE PORT: 옵션>
```
- 위 명령어 사용시 `java 빌드 -> docker 빌드 -> docker push -> docker run` 순서로 진행됩니다.
- `SERVICE PORT` 는 외부에서 접근 가능한 포트입니다.

```
cd docker
docker compose up -d
```
- 위 명령어 사용시 kafka, kafka-ui 가 동작합니다.

### 테스트

```
curl -X GET "http://localhost:8080/demo/message?message=Hello"
```
- 초기 상태일 때 테스트 가능
- `Message published successfully!` 라는 메세지 수신 시 테스트 성공

## 서비스 변경

- 전체적으로 `example.demo` 로 되어있는 모든 것을 수정해주세요.
- 수정 후 `gateway` 저장소의 서비스도 수정해야합니다. 
