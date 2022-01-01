package com.dal.hiree.entity;

public class Job {
    int id;
    int recruiterId;
    String jobHeadline;
    String jobDescription;
    String dateOfPosting;
    boolean isActive;

    public Job(int id, int recruiterId, String jobHeadline, String jobDescription, String dateOfPosting, boolean isActive) {
        this.id = id;
        this.recruiterId = recruiterId;
        this.jobHeadline = jobHeadline;
        this.jobDescription = jobDescription;
        this.dateOfPosting = dateOfPosting;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public String getJobHeadline() {
        return jobHeadline;
    }

    public void setJobHeadline(String jobHeadline) {
        this.jobHeadline = jobHeadline;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(String dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
