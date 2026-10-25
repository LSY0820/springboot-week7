-- [6주차 제공] 관리자(ADMIN) 계정 만들기
-- 1) 먼저 회원가입 화면(/signup)에서 아이디 admin , 비밀번호 1234 로 가입한다. (비밀번호는 BCrypt 로 저장됨)
-- 2) MySQL 확장에서 아래 SQL 을 실행해 admin 의 권한을 ADMIN 으로 바꾼다.
-- 3) 이미 admin 으로 로그인 중이었다면 → 로그아웃 후 다시 로그인해야 새 권한이 적용된다.

-- 현재 회원과 권한 확인
SELECT id, username, name, role FROM member;

-- admin 계정을 관리자로 지정
UPDATE member SET role = 'ADMIN' WHERE username = 'admin';

-- 결과 확인 (admin 의 role 이 ADMIN 인지)
SELECT id, username, name, role FROM member;

-- (참고) 비밀번호를 SQL 로 직접 넣으면 안 되는 이유
-- INSERT INTO member (username, password, name, role) VALUES ('admin2', '1234', '관리자2', 'ADMIN');
--   → 평문 '1234' 는 BCrypt 형식이 아니므로 로그인 불가 + 개인정보 보호 위반! 반드시 가입 기능으로 생성.
