package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// [7주차 연습문제 ②] API 오류 처리를 한 곳에 모음
// - API 컨트롤러에서 IllegalArgumentException 이 나면 여기서 400 {"error": "메시지"} 로 응답
// - 화면용 컨트롤러(AdminController 등)는 지금처럼 flash 메시지로 처리해야 해서 API 컨트롤러에만 적용
// - 권한 없음(403)은 시큐리티가 처리하므로 여기서 잡지 않음
@RestControllerAdvice(assignableTypes = { AdminApiController.class, MemberApiController.class })
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class) // 중복 아이디, 빈 값, 허용 안 되는 권한, 본인 보호, 없는 회원 ...
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // 400
    }
}
