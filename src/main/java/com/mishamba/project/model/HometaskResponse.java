package com.mishamba.project.model;

public class HometaskResponse {
    private int  hometaskId;
    private int studentId;
    private String answer;
    private Integer mark;

    public HometaskResponse(int hometaskId, int studentId, String answer,
                            Integer mark) {
        this.hometaskId = hometaskId;
        this.studentId = studentId;
        this.answer = answer;
        this.mark = mark;
    }

    public int getHometaskId() {
        return hometaskId;
    }

    public void setHometaskId(int hometaskId) {
        this.hometaskId = hometaskId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HometaskResponse that = (HometaskResponse) o;
        return hometaskId == that.hometaskId &&
                studentId == that.studentId &&
                answer.equals(that.answer) &&
                mark.equals(that.mark);
    }

    @Override
    public int hashCode() {
        int prime = 43;
        int hash =1;
        hash *= hometaskId * prime;
        hash *= studentId * prime;
        hash *= (answer != null) ? answer.hashCode() * prime : 1;
        hash *= (mark != null) ? mark.hashCode() * prime : 1;

        return hash;
    }
}
