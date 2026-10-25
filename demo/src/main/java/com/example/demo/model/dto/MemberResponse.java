package com.example.demo.model.dto;

import com.example.demo.model.domain.Member;

// API 응답용 DTO : 화면(JSON)에 내보낼 값만 담는다 → password 는 빠진다
public record MemberResponse(Long id, String username, String name, String role) {
    public static MemberResponse from(Member member) { // 엔티티 → 응답 DTO 변환
        return new MemberResponse(member.getId(), member.getUsername(), member.getName(), member.getRole());
    }
}
