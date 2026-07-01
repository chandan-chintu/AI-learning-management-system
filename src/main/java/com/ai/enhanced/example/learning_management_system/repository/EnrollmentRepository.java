package com.ai.enhanced.example.learning_management_system.repository;

import com.ai.enhanced.example.learning_management_system.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query(nativeQuery = true, value = "select * from enrollments where student_id = :studentId")
    List<Enrollment> findByStudentId(Long studentId);
}
