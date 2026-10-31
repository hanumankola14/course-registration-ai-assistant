package com.courseregistration.model;

import java.util.*;

public class Course {

    private String courseId;
    private int capacity;

    private Set<String> enrolledStudents = new HashSet<>();

    private Queue<Student> waitlist =
            new LinkedList<>();

    public Course(String cs101, int i, ArrayList<Object> objects, LinkedList<Object> objects1) {
    }

    // getters setters

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public Queue<Student> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(Queue<Student> waitlist) {
        this.waitlist = waitlist;
    }
}