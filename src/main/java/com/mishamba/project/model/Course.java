package com.mishamba.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    Integer id;
    String courseName;
    Date beginOfCourse;
    Date endOfCourse;
    User teacher;
    Integer maxStudentQuantity;
    List<ProgramStep> courseProgram;
    Boolean finished;

    public Course(Integer id, String courseName, Date beginOfCourse,
                  Date endOfCourse, User teacher, Integer maxStudentQuantity,
                  List<ProgramStep> courseProgram, Boolean finished) {
        this.id = id;
        this.courseName = courseName;
        this.beginOfCourse = beginOfCourse;
        this.endOfCourse = endOfCourse;
        this.teacher = teacher;
        this.maxStudentQuantity = maxStudentQuantity;
        this.courseProgram = courseProgram;
        this.finished = finished;
    }

    public List<ProgramStep> getCourseProgram() {
        return courseProgram;
    }

    public void setCourseProgram(ArrayList<ProgramStep> courseProgram) {
        this.courseProgram = courseProgram;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getBeginOfCourse() {
        return beginOfCourse;
    }

    public void setBeginOfCourse(Date beginOfCourse) {
        this.beginOfCourse = beginOfCourse;
    }

    public Date getEndOfCourse() {
        return endOfCourse;
    }

    public void setEndOfCourse(Date endOfCourse) {
        this.endOfCourse = endOfCourse;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Integer getMaxStudentQuantity() {
        return maxStudentQuantity;
    }

    public void setMaxStudentQuantity(Integer maxStudentQuantity) {
        this.maxStudentQuantity = maxStudentQuantity;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != Course.class) {
            return false;
        }

        Course course = (Course) o;
        return getId().equals(course.getId()) &&
                getCourseName().equals(course.getCourseName()) &&
                getBeginOfCourse().equals(course.getBeginOfCourse()) &&
                getEndOfCourse().equals(course.getEndOfCourse()) &&
                getTeacher().equals(course.getTeacher()) &&
                getMaxStudentQuantity().equals(course.getMaxStudentQuantity()) &&
                getFinished().equals(course.getFinished());
    }

    @Override
    public int hashCode() {
        int hash;
        int prime = 63;
        hash = id * prime;
        hash += courseName.hashCode() * prime;
        hash += beginOfCourse.hashCode() * prime;
        hash += endOfCourse.hashCode() * prime;
        hash += teacher.hashCode() * prime;
        hash += maxStudentQuantity.hashCode() * prime;
        hash += courseProgram.hashCode() * prime;
        hash += finished.hashCode() * prime;

        return hash;
    }
}
