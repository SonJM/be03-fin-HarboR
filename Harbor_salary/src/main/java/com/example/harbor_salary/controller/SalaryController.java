package com.example.harbor_salary.controller;

import com.example.harbor_salary.dto.request.MySalaryRequest;
import com.example.harbor_salary.service.SalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;


    }
    // 개인 급여 목록 조회
    @GetMapping("/mysalary")
        public List<MySalaryRequest> findMySalary(){
            return salaryService.findMySalary();
        }
}