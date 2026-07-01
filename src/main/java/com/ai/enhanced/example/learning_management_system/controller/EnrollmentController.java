package com.ai.enhanced.example.learning_management_system.controller;

import com.ai.enhanced.example.learning_management_system.model.Enrollment;
import com.ai.enhanced.example.learning_management_system.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    // Enroll Student
    @PostMapping("/save")
    public Enrollment enrollStudent(@RequestParam Long studentId,
                                    @RequestParam Long courseId) {

        return enrollmentService.enrollStudent(studentId, courseId);
    }

    // Get All Enrollments
    @GetMapping("/findAll")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    // Get Student Enrollments
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getStudentEnrollments(
            @PathVariable Long studentId) {

        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

}
