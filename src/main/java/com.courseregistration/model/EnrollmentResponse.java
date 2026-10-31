package com.courseregistration.model;

public record EnrollmentResponse(
        boolean success,
        String message,
        EnrollmentStatus status,
        String studentId,
        String courseId
) {
    // Static factory methods for convenience
    public static EnrollmentResponse success(String message, EnrollmentStatus status, String studentId, String courseId) {
        return new EnrollmentResponse(true, message, status, studentId, courseId);
    }

    public static EnrollmentResponse failure(String message, String studentId, String courseId) {
        return new EnrollmentResponse(false, message, null, studentId, courseId);
    }
}