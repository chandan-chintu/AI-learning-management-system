package com.ai.enhanced.example.learning_management_system.controller;


import com.ai.enhanced.example.learning_management_system.model.Student;
import com.ai.enhanced.example.learning_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    // Create Student
    @PostMapping("/save")
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // Get All Students
    @GetMapping("/findAll")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get Student By Id
    @GetMapping("findById/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

}