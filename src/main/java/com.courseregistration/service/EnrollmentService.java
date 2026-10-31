package com.courseregistration.service;

import com.courseregistration.model.Course;
import com.courseregistration.model.Student;
import com.courseregistration.repository.InMemoryCourseRepository;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    private final InMemoryCourseRepository repo;

    public EnrollmentService(
            InMemoryCourseRepository repo) {
        this.repo = repo;
    }

    public synchronized String enroll(
            String courseId,
            Student student) {

        Course course = repo.getCourse(courseId);

        if (!student.prerequisiteCompleted())
            return "PREREQUISITE_NOT_MET";

        if (course.getEnrolledStudents()
                .contains(student.studentId()))
            return "ALREADY_ENROLLED";

        boolean alreadyWaitlisted =
                course.getWaitlist().stream()
                        .anyMatch(s ->
                                s.studentId()
                                        .equals(student.studentId()));

        if (alreadyWaitlisted)
            return "ALREADY_WAITLISTED";

        if (course.getEnrolledStudents().size()
                < course.getCapacity()) {

            course.getEnrolledStudents()
                    .add(student.studentId());

            return "ENROLLED";
        }

        course.getWaitlist().offer(student);

        return "WAITLISTED";
    }
    public synchronized String drop(
            String courseId,
            String studentId) {

        Course course = repo.getCourse(courseId);

        boolean removed =
                course.getEnrolledStudents()
                        .remove(studentId);

        if (!removed)
            return "NOT_FOUND";

        promoteWaitlistedStudent(course);

        return "DROPPED";
    }
    private void promoteWaitlistedStudent(
            Course course) {

        while (!course.getWaitlist().isEmpty()) {

            Student next =
                    course.getWaitlist().poll();

            if (next.prerequisiteCompleted()) {

                course.getEnrolledStudents()
                        .add(next.studentId());

                return;
            }
        }
    }

}