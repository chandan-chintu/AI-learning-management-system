package com.ai.enhanced.example.learning_management_system.service;

import com.ai.enhanced.example.learning_management_system.model.Course;
import com.ai.enhanced.example.learning_management_system.model.Enrollment;
import com.ai.enhanced.example.learning_management_system.repository.CourseRepository;
import com.ai.enhanced.example.learning_management_system.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    EnrollmentRepository enrollmentRepository;

    // RestTemplate - calls API from other projects
    private final RestTemplate restTemplate = new RestTemplate();

    // Generate AI summary for a course
    public String summarizeCourse(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String prompt = """
                Summarize the following course in 100 words.

                Title:
                %s

                Description:
                %s
                """
                .formatted(course.getTitle(), course.getDescription());

        return callGemini(prompt);
    }

    // Recommend next course
    public String suggestNextCourse(Long studentId) {

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(studentId);

        StringBuilder prompt = new StringBuilder();

        prompt.append("Student has completed these courses:\n");

        for (Enrollment e : enrollments) {
            prompt.append("- ")
                    .append(e.getCourse().getTitle())
                    .append("\n");
        }

        prompt.append("""
                
                Recommend the next course with a short reason.
                Return only the recommendation.
                """);

        return callGemini(prompt.toString());
    }

    // Call Gemini API
    private String callGemini(String prompt) {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                        + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(prompt.replace("\"", "\\\""));

        HttpEntity<String> entity =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        Map.class);

        Map body = response.getBody();

        List candidates = (List) body.get("candidates");
        Map candidate = (Map) candidates.get(0);
        Map content = (Map) candidate.get("content");
        List parts = (List) content.get("parts");
        Map part = (Map) parts.get(0);

        return part.get("text").toString();
    }
}
