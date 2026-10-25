package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.service.MemberService;

@Controller // 컨트롤러 어노테이션 명시
public class MemberController {

    @Autowired
    MemberService memberService; // 클래스 상단에 객체 주입

    @GetMapping("/login") // 로그인 화면 (POST /login 은 시큐리티가 처리)
    public String login() {
        return "login"; // login.html 연결
    }

    @GetMapping("/signup") // 회원가입 화면
    public String signupForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "signup"; // signup.html 연결
    }

    @PostMapping("/signup") // 회원가입 처리
    public String signup(MemberForm memberForm, Model model) {
        try {
            memberService.signup(memberForm);
        } catch (IllegalArgumentException e) { // 중복 아이디, 비밀번호 불일치 등
            model.addAttribute("error", e.getMessage()); // ${error}
            return "signup"; // 입력값 유지한 채 다시 가입 화면
        }
        return "redirect:/login?signup"; // 성공 → 로그인 화면
    }

    @GetMapping("/mypage") // [6주차] 내 정보 : 로그인한 사람만
    public String mypage(Principal principal, Model model) { // 현재 로그인 사용자
        // /mypage?id=3 처럼 번호를 받지 않음 → 번호 조작(IDOR)으로 남의 정보 조회 차단
        Member member = memberService.findByUsername(principal.getName());
        model.addAttribute("member", member);
        return "mypage"; // mypage.html 연결
    }
}
