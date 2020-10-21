package com.mishamba.project.model;

import java.util.Date;
import java.util.Objects;

public class MarkReview {
    private User student;
    private User teacher;
    private Course course;
    private Boolean finished;
    private int mark;
    private Date finishDate;
    private String review;
    private Boolean gotCertificate;

    public MarkReview(User student, User teacher, Course course,
                      Boolean finished, Integer mark, Date finishDate,
                      String review, Boolean gotCertificate) {
        this.student = student;
        this.teacher = teacher;
        this.course = course;
        this.finished = finished;
        this.mark = mark;
        this.finishDate = finishDate;
        this.review = review;
        this.gotCertificate = gotCertificate;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Boolean getGotCertificate() {
        return gotCertificate;
    }

    public void setGotCertificate(Boolean gotCertificate) {
        this.gotCertificate = gotCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarkReview that = (MarkReview) o;
        return student.equals(that.student) &&
                teacher.equals(that.teacher) &&
                course.equals(that.course) &&
                finished.equals(that.finished) &&
                mark == that.mark &&
                review.equals(that.review) &&
                gotCertificate.equals(that.gotCertificate);
    }

    @Override
    public int hashCode() {
        int hash;
        int prime = 12;
        hash = student.hashCode() * prime;
        hash += teacher.hashCode() * prime;
        hash += course.hashCode() * prime;
        hash += finished.hashCode() * prime;
        hash += finishDate.hashCode() * prime;
        hash += review.hashCode() * prime;
        hash += gotCertificate.hashCode() * prime;

        return hash;
    }
}
