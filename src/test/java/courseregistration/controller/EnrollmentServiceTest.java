package courseregistration.controller;

import com.courseregistration.model.Course;
import com.courseregistration.model.Student;
import com.courseregistration.repository.InMemoryCourseRepository;
import com.courseregistration.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private InMemoryCourseRepository repo;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Course course;
    private Student student;

    @BeforeEach
    void setUp() {
        // Initialize standard course and student for testing
        course = new Course("CS101", 2, new ArrayList<>(), new LinkedList<>());
        student = new Student("S1", true);
    }

    @Test
    void testEnroll_PrerequisiteNotMet() {
        Student unfulfilledStudent = new Student("S2", false);

        String result = enrollmentService.enroll("CS101", unfulfilledStudent);

        assertEquals("PREREQUISITE_NOT_MET", result);
    }

    @Test
    void testEnroll_AlreadyEnrolled() {
        course.getEnrolledStudents().add("S1");
        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.enroll("CS101", student);

        assertEquals("ALREADY_ENROLLED", result);
    }

    @Test
    void testEnroll_AlreadyWaitlisted() {
        course.getWaitlist().offer(student);
        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.enroll("CS101", student);

        assertEquals("ALREADY_WAITLISTED", result);
    }

    //@Test
    void testEnroll_SuccessfullyEnrolled() {
        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.enroll("CS101", student);

        assertEquals("ENROLLED", result);
        assertTrue(course.getEnrolledStudents().contains("S1"));
    }

    @Test
    void testEnroll_WaitlistedWhenCapacityFull() {
        course.getEnrolledStudents().add("S2");
        course.getEnrolledStudents().add("S3"); // Capacity is 2
        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.enroll("CS101", student);

        assertEquals("WAITLISTED", result);
        assertTrue(course.getWaitlist().contains(student));
    }

    @Test
    void testDrop_NotFound() {
        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.drop("CS101", "S1");

        assertEquals("NOT_FOUND", result);
    }

    @Test
    void testDrop_SuccessAndPromoteWaitlisted() {
        course.getEnrolledStudents().add("S1");

        Student waitlistedStudent = new Student("S2", true);
        course.getWaitlist().offer(waitlistedStudent);

        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.drop("CS101", "S1");

        assertEquals("DROPPED", result);
        assertFalse(course.getEnrolledStudents().contains("S1"));
        assertTrue(course.getEnrolledStudents().contains("S2")); // Promoted
        assertTrue(course.getWaitlist().isEmpty());
    }

    @Test
    void testDrop_SuccessSkipInvalidWaitlistedStudent() {
        course.getEnrolledStudents().add("S1");

        Student invalidWaitlisted = new Student("S2", false);
        Student validWaitlisted = new Student("S3", true);

        course.getWaitlist().offer(invalidWaitlisted);
        course.getWaitlist().offer(validWaitlisted);

        when(repo.getCourse("CS101")).thenReturn(course);

        String result = enrollmentService.drop("CS101", "S1");

        assertEquals("DROPPED", result);
        assertFalse(course.getEnrolledStudents().contains("S1"));
        assertFalse(course.getEnrolledStudents().contains("S2")); // Skipped because prereq not met
        assertTrue(course.getEnrolledStudents().contains("S3")); // Promoted
    }
}