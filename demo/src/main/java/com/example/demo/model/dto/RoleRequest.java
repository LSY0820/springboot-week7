package com.example.demo.model.dto;

// 권한 변경 요청 DTO : 본문 {"role": "ADMIN"} 을 받는다
public record RoleRequest(String role) {
}
