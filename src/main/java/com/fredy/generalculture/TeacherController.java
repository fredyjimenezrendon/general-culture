package com.fredy.generalculture;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Controller
public class TeacherController {

    private static final String code = "6725";

    private final ReactiveRedisOperations<String, StudentRequest> studentRequestOps;
    private final ReactiveRedisConnectionFactory factory;

    public TeacherController(ReactiveRedisOperations<String, StudentRequest> studentRequestOps, ReactiveRedisConnectionFactory factory) {
        this.studentRequestOps = studentRequestOps;
        this.factory = factory;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin.html");
    }

    @PostMapping("/auth")
    @ResponseBody
    public boolean auth(@RequestParam String codeInRequest) {
        if (code.equals(codeInRequest)) {
            next();
           return true;
        } else {
            return false;
        }
    }

    @PostMapping("/next")
    @ResponseBody
    public void next() {
        studentRequestOps.keys("*").flatMap(student -> studentRequestOps.delete(student)).subscribe(System.out::println);
    }

    @GetMapping("/get-students")
    @ResponseBody
    public Flux<StudentRequest> all() {
        return studentRequestOps.keys("*").flatMap(studentRequestOps.opsForValue()::get).sort(Comparator.comparing(StudentRequest::getTime));
    }

}
