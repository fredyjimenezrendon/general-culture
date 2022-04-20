package com.fredy.generalculture;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;

@Controller
public class TeacherController {

    private static final String code = "6725";

    private final ReactiveRedisOperations<String, StudentRequest> studentRequestOps;

    public TeacherController(ReactiveRedisOperations<String, StudentRequest> studentRequestOps) {
        this.studentRequestOps = studentRequestOps;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin.html");
    }

    @PostMapping("/auth")
    @ResponseBody
    public boolean auth(@RequestParam String codeInRequest) {
        if (code.equals(codeInRequest)) {
           return true;
        } else {
            return false;
        }
    }

    @GetMapping("/get-students")
    @ResponseBody
    public Flux<StudentRequest> all() {
        return studentRequestOps.keys("*").flatMap(studentRequestOps.opsForValue()::get);
    }

}
