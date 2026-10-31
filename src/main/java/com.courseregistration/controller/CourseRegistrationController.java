package com.courseregistration.controller;

import com.courseregistration.model.Student;
import com.courseregistration.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseRegistrationController {

    private final EnrollmentService service;

    public CourseRegistrationController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping("/{courseId}/enroll")
    public String enroll(
            @PathVariable String courseId,
            @RequestBody Student student) {

        return service.enroll(
                courseId,
                student);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public String drop(
            @PathVariable String courseId,
            @PathVariable String studentId) {

        return service.drop(
                courseId,
                studentId);
    }
}