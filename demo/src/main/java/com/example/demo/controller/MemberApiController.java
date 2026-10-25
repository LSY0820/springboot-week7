package com.example.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberResponse;
import com.example.demo.model.service.MemberService;

@RestController // 화면(HTML) 대신 데이터(JSON)를 응답하는 컨트롤러
@RequestMapping("/api") // 이 컨트롤러의 모든 URL 앞에 /api
public class MemberApiController {

    @Autowired
    MemberService memberService;

    @GetMapping("/hello") // 첫 번째 API : GET /api/hello (누구나)
    public Map<String, Object> hello() { // Map 을 반환하면 스프링이 JSON 으로 바꿔 준다
        return Map.of("message", "안녕하세요, REST API!", "time", LocalDateTime.now().withNano(0));
    }

    @GetMapping("/me") // 내 정보 API : GET /api/me (로그인 필요)
    public MemberResponse me(Principal principal) { // 6주차 /mypage 와 같은 방법
        Member member = memberService.findByUsername(principal.getName());
        return MemberResponse.from(member); // 엔티티 대신 DTO 반환 → 비밀번호(해시) 노출 차단
    }
}
