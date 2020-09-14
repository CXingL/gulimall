package com.atguigu.eduservice.entity.course;

public enum CourseStatus {
    NORMAL("Normal"), DRAFT("Draft");

    private String status;

    private CourseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
