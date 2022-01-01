package com.dal.hiree.entity;

public class AppliedJob {
    int id;
    int jobId;
    int candidateId;
    String dateOfApplication;
    String result;

    public AppliedJob(int id, int jobId, int candidateId, String dateOfApplication, String result) {
        this.id = id;
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.dateOfApplication = dateOfApplication;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(String dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
