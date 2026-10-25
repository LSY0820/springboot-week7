package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;

@Service // 서비스 등록, 자동 등록됨
public class TestService {

    @Autowired // 객체 의존성 주입 DI(컨테이너 내부 등록)
    private TestRepository testRepository;

    // public TestDB findByName(String name) { // 이름 찾기 (1명 출력할 때 사용)
    //     return (TestDB) testRepository.findByName(name);
    // }

    public List<TestDB> findAll() { // 전체 조회 (SELECT * FROM testdb)
        return testRepository.findAll();
    }
}
