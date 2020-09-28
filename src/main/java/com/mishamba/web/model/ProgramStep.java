package com.mishamba.web.model;

import java.util.Date;

public class ProgramStep {
    Integer courseId;
    int step;
    String stepName;
    String description;
    Date startDate;
    Date endDate;

    public ProgramStep(Integer courseId, int step, String stepName, String description,
                       Date startDate, Date endDate) {
        this.courseId = courseId;
        this.step = step;
        this.stepName = stepName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != ProgramStep.class) {
            return false;
        }

        ProgramStep that = (ProgramStep) o;
        return getStep() == that.getStep() &&
                getStepName().equals(that.getStepName()) &&
                getDescription().equals(that.getDescription()) &&
                getStartDate().equals(that.getStartDate()) &&
                getEndDate().equals(that.getEndDate());
    }

    @Override
    public int hashCode() {
        int hash;
        int prime = 62;
        hash = courseId.hashCode() * prime;
        hash += step * prime;
        hash += stepName.hashCode() * prime;
        hash += description.hashCode() * prime;
        hash += startDate.hashCode() * prime;
        hash += endDate.hashCode() * prime;

        return hash;
    }
}
