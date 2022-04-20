package com.fredy.generalculture;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StudentController {

    private final ReactiveRedisOperations<String, StudentRequest> studentRequestOps;

    public StudentController(ReactiveRedisOperations<String, StudentRequest> studentRequestOps) {
        this.studentRequestOps = studentRequestOps;
    }


    @PostMapping("/student")
    public void student(@RequestParam String studentName) {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setTime(String.valueOf(new Date().getTime()));
        studentRequest.setName(studentName);

        boolean isPresent = studentRequestOps.keys("*").flatMap(studentRequestOps.opsForValue()::get).toStream().anyMatch(o -> studentName.equals(o.getName()));

        if (!isPresent) {
            studentRequestOps.opsForValue().set(String.valueOf(new Date().getTime()), studentRequest).thenMany(studentRequestOps.keys("*")
                            .flatMap(studentRequestOps.opsForValue()::get))
                    .subscribe();
        }
    }
}
