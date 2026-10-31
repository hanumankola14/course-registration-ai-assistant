package com.courseregistration.repository;

import com.courseregistration.model.Course;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCourseRepository {

    private final Map<String, Course> courses =
            new ConcurrentHashMap<>();

    public Course getCourse(String courseId){
        return courses.get(courseId);
    }

    public void save(Course course){
        courses.put(course.getCourseId(), course);
    }
}