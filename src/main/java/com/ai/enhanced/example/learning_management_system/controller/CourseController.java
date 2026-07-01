package com.ai.enhanced.example.learning_management_system.controller;

import com.ai.enhanced.example.learning_management_system.model.Course;
import com.ai.enhanced.example.learning_management_system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    // Create Course
    @PostMapping("/save")
    public Course createCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    // Get All Courses
    @GetMapping("/findAll")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Get Course By Id
    @GetMapping("/findById/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

}