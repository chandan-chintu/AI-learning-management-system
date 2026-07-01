package com.ai.enhanced.example.learning_management_system.controller;


import com.ai.enhanced.example.learning_management_system.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    AIService aiService;

    // AI Summary

    @PostMapping("/summarize-course")
    public String summarizeCourse(@RequestParam Long courseId) {

        return aiService.summarizeCourse(courseId);

    }

    // AI Recommendation

    @PostMapping("/suggest-next")
    public String suggestNextCourse(@RequestParam Long studentId) {

        return aiService.suggestNextCourse(studentId);

    }

}