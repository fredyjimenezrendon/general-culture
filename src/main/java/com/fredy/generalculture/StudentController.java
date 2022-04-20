package com.fredy.generalculture;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StudentController {

    private final ReactiveRedisOperations<String, StudentRequest> studentRequestOps;
    private final ReactiveRedisConnectionFactory factory;

    public StudentController(ReactiveRedisOperations<String, StudentRequest> studentRequestOps, ReactiveRedisConnectionFactory factory) {
        this.studentRequestOps = studentRequestOps;
        this.factory = factory;
    }


    @PostMapping("/student")
    public void student(@RequestParam String studentName) {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setName(studentName);
        studentRequestOps.convertAndSend(new Date().toString(), studentRequest);
    }
}
