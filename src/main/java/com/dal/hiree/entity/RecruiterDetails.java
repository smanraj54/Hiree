package com.dal.hiree.entity;

public class RecruiterDetails {
    int id;
    int recruiterId;
    String DOJ;
    String companyDetails;

    public RecruiterDetails(int id, int recruiterId, String DOJ, String companyDetails) {
        this.id = id;
        this.recruiterId = recruiterId;
        this.DOJ = DOJ;
        this.companyDetails = companyDetails;
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

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }
}
