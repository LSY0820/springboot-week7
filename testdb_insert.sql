-- 4주차 연습문제: VS Code MySQL 확장에서 실행 (서버를 한 번 실행해서 testdb 테이블이 자동으로 만들어진 다음에)
USE spring;
INSERT INTO testdb (name, age, gender) VALUES ('홍길동', 25, '남');
INSERT INTO testdb (name, age, gender) VALUES ('김영희', 22, '여');
INSERT INTO testdb (name, age, gender) VALUES ('이철수', 30, '남');
SELECT * FROM testdb;
