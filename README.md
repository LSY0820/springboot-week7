# 자바웹프로그래밍(2) 실습

## 7주차 - 회원 관리 REST API(JSON) 완료
- 화면은 그대로 두고 데이터(JSON)만 주고받는 API 추가 (`@RestController` → 객체를 반환하면 JSON 으로 자동 변환)
- [MemberApiController.java](demo/src/main/java/com/example/demo/controller/MemberApiController.java): `GET /api/hello` (누구나), `GET /api/me` (내 정보)
- [MemberResponse.java](demo/src/main/java/com/example/demo/model/dto/MemberResponse.java) (record): 응답 DTO → **비밀번호(해시) 노출 차단**. 엔티티를 그대로 반환하면 getter 가 있는 값(password)이 전부 JSON 으로 나감
- [AdminApiController.java](demo/src/main/java/com/example/demo/controller/AdminApiController.java): 주소는 하나, 할 일은 HTTP 메서드로
- [RoleRequest.java](demo/src/main/java/com/example/demo/model/dto/RoleRequest.java): 권한 변경 요청 `{"role":"ADMIN"}`
- [SecurityConfig.java](demo/src/main/java/com/example/demo/config/SecurityConfig.java): `/api/admin/**` 관리자 전용, `httpBasic` (Postman 은 쿠키가 없어 요청마다 Basic 인증), `/api/**` CSRF 제외, 로그인 안 한 API 요청은 로그인 화면(302) 대신 **401**
- 화면: [admin/members-api.html](demo/src/main/resources/templates/admin/members-api.html) (제공 코드, fetch 로 API 호출 · 새로고침 없음), 회원 관리 화면에 "API 버전으로 보기" 링크
- [Postman 컬렉션](7주차_postman_collection.json): Import 해서 바로 테스트 (`baseUrl`, `memberId` 변수)

| 하고 싶은 일 | 6주차 화면 방식 | 7주차 REST 방식 | 성공 코드 |
|---|---|---|---|
| 회원 목록 | GET /admin/members | GET /api/admin/members | 200 |
| 회원 1명 | - | GET /api/admin/members/{id} | 200 / 없으면 404 |
| 회원 등록 | POST /signup | POST /api/admin/members | 201 + Location |
| 권한 변경 | POST /admin/members/{id}/role | PATCH /api/admin/members/{id}/role | 200 |
| 회원 삭제 | POST /admin/members/{id}/delete | DELETE /api/admin/members/{id} | 204 |

## 7주차 연습문제 - 회원 검색 API & 오류 처리 한 곳에 모으기
1. **회원 검색**: `GET /api/admin/members?keyword=student` → 아이디나 이름에 검색어가 들어간 회원만 (`@RequestParam(required = false)`, `findByUsernameContainingOrNameContaining`)
2. **오류 처리 한 곳에**: 등록·수정·삭제마다 반복되던 try-catch 를 지우고 [ApiExceptionHandler.java](demo/src/main/java/com/example/demo/controller/ApiExceptionHandler.java) (`@RestControllerAdvice`) 한 곳에서 `IllegalArgumentException → 400 {"error": "..."}` 처리 → 지운 뒤에도 상태 코드·메시지 동일
   - 추가: 오류 JSON 에 서버 내부 정보(trace)가 안 나가게 `server.error.include-stacktrace=never`

### API 테스트 결과 (curl / Postman 과 같은 요청)
| # | 요청 | 계정 | 결과 |
|---|---|---|---|
| 1 | GET /api/hello | 없음 | 200 `{"message":"안녕하세요, REST API!", "time":...}` |
| 2 | GET /api/me | 없음 | **401** (로그인 화면 HTML 아님) |
| 3 | GET /api/me | 학생 | 200 `{"id","username","name","role"}` — **password 없음** |
| 4 | GET /api/me | 비밀번호 틀림 | 401 |
| 5 | GET /api/admin/members?keyword=학생 | admin | 200, 이름에 "학생"이 들어간 회원만 |
| 6 | GET /api/admin/members/999999 | admin | 404 |
| 7 | GET /api/admin/members | 학생(USER) | 403 |
| 8 | POST /api/admin/members (`"role":"ADMIN"` 몰래 포함) | admin | 201 + `Location`, role 은 그대로 USER |
| 9 | 같은 아이디 다시 등록 / 아이디만 보내기 | admin | 400 `이미 사용 중인 아이디입니다.` / 400 `아이디, 비밀번호, 이름은 필수입니다.` |
| 10 | PATCH .../role `{"role":"ADMIN"}` | admin | 200 |
| 11 | PATCH .../role `{"role":"SUPER"}` | admin | 400 `허용되지 않는 권한입니다 : SUPER` |
| 12 | PATCH 본인 권한 | admin | 400 `자기 자신의 권한 변경·삭제는 할 수 없습니다.` |
| 13 | DELETE /api/admin/members/{id} | 학생(USER) | 403 |
| 14 | DELETE /api/admin/members/{id} | admin | 204 |
| 15 | 화면 /mypage (비로그인) | 없음 | 302 → 로그인 화면 (화면은 기존 그대로) |

## 실행 방법
1. `demo/src/main/resources/application-secret.properties` 작성 (깃에 안 올라감)
   ```
   spring.datasource.username=root
   spring.datasource.password=내비밀번호
   ```
2. `DemoApplication.java` → `Ctrl + F5` → http://localhost:8080
3. Postman → Import → `7주차_postman_collection.json`

## 이전 주차
- [2주차](https://github.com/LSY0820/springboot-week2) · [3주차](https://github.com/LSY0820/springboot-week3) · [4주차](https://github.com/LSY0820/springboot-week4) · [5주차](https://github.com/LSY0820/springboot-week5) · [6주차](https://github.com/LSY0820/springboot-week6)
