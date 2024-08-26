

# 🚀데일리 트래블: 일상을 여행으로 만드는 팀 프로젝트

### 개요
'데일리 트래블'은 사용자가 자유롭게 여행 장소와 일정을 공유할 수 있는 웹사이트입니다. 이 프로젝트는 팀 협업을 통해 개발되었으며, 여행을 좋아하는 사람들에게 유용한 정보를 제공하는 것을 목표로 합니다.
![image](https://github.com/user-attachments/assets/50b6b241-e5e1-4f9f-97f9-3a56ab93caee)


## Team 🏃‍♂️

| <img src="https://avatars.githubusercontent.com/u/22585023?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/64997345?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/102151689?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/102151689?v=4" width="150" height="150"/> |
| :----------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
|                           [@recoild](https://github.com/recoild)                           |                       [@ChoiYoungHa](https://github.com/ChoiYoungHa)                       |                            [@0lYUMA](https://github.com/0lYUMA)                             |                            [@jjeong1015](https://github.com/jjeong1015)                      |

### 역할 분담

- **[0lYUMA](https://github.com/0lYUMA)**
  - **팀장**
  - 백엔드 개발
  - 게시판 서비스 개발
  - 게시판 성능 최적화
  - 이미지 서버 연동

- **[recoild](https://github.com/recoild)**
  - 팀원
  - 프론트엔드 및 백엔드 개발
  - 유저 서비스 개발
  - 게시판 성능 최적화

- **[ChoiYoungHa](https://github.com/ChoiYoungHa)**
  - 팀원
  - 백엔드 개발
  - 좋아요 서비스 개발
  - 좋아요 성능 최적화

- **[jjeong1015](https://github.com/jjeong1015)**
  - 팀원
  - 백엔드 개발
  - 댓글 서비스 개발
  - 댓글 성능 최적화


## 주요 기능
- 여행 게시글 작성 및 공유
- 사용자 간 소통 기능 (댓글, 좋아요)
- DB 스키마 유지 및 버전 관리를 위한 Flyway 도입
- 구글 로그인 인증 후 인가를 담당하는 리소스 서버 기능
- 트위터 스타일의 무한 스크롤링 게시글 목록 조회 기능

## 사용 기술 스택
- **프론트엔드**: TypeScript, Next.js, Tailwind CSS, ShadcnUI
- **백엔드**: Spring Boot 3, JPA
- **데이터베이스**: Oracle DB,  Redis DB
- **검색 서비스**: ELK Stack (Elasticsearch, Logstash, Kibana)
- **빌드 도구**: Gradle
- **컨테이너화**: Docker Compose


## 로컬 개발 환경
![1](https://github.com/user-attachments/assets/cddfd0f4-1713-4ee6-8685-1df46b36bc5a)

## 환경 설정
팀원들은 사전에 공유한 env파일들을 백엔드, 프론트엔드 저장소 폴더에 붙여넣기 합니다.

## 실행 순서
1. 도커 컴포즈 실행
```
docker compose up -d
```
2. 스프링 부트 서버 실행
3. (선택) 프론트엔드 실행
