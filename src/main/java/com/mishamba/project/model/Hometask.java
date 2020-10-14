package com.mishamba.project.model;

import java.util.Date;

public class Hometask {
    private int courseId;
    private Integer id;
    private String title;
    private String description;
    private Date beginDate;
    private Date deadline;
    private HometaskResponse response;

    public Hometask(int courseId, Integer id, String title, String description,
                    Date beginDate, Date deadline, HometaskResponse response) {
        this.courseId = courseId;
        this.id = id;
        this.title = title;
        this.description = description;
        this.beginDate = beginDate;
        this.deadline = deadline;
        this.response = response;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer hometaskId) {
        this.id = hometaskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public HometaskResponse getResponse() {
        return response;
    }

    public void setResponse(HometaskResponse response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hometask hometask = (Hometask) o;
        return courseId == hometask.courseId &&
                response.equals(hometask.getResponse()) &&
                id.equals(hometask.id) &&
                title.equals(hometask.title) &&
                description.equals(hometask.description) &&
                beginDate.equals(hometask.beginDate) &&
                deadline.equals(hometask.deadline);
    }

    @Override
    public int hashCode() {
        int prime = 53;
        int hash = 1;
        hash *= (id != null) ? id * prime : 2;
        hash *= courseId * prime;
        hash *= title.hashCode() * prime;
        hash *= description.hashCode() * prime;
        hash *= beginDate.hashCode() * prime;
        hash *= deadline.hashCode() * prime;
        hash *= (response != null) ? response.hashCode() * prime : 1;

        return hash;
    }
}
