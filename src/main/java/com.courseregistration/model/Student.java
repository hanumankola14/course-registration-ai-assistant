package com.courseregistration.model;

public record Student(
        String studentId,
        boolean prerequisiteCompleted) {
}