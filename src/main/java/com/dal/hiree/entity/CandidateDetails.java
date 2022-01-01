package com.dal.hiree.entity;

public class CandidateDetails {
    int id;
    int candidateId;
    String DOB;
    String aboutMe;

    public CandidateDetails(int id, int candidateId, String DOB, String aboutMe) {
        this.id = id;
        this.candidateId = candidateId;
        this.DOB = DOB;
        this.aboutMe = aboutMe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @Override
    public String toString() {
        return "CandidateDetails{" +
                "id=" + id +
                ", candidateId=" + candidateId +
                ", DOB='" + DOB + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }
}
