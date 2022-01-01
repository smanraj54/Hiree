package com.dal.hiree.dao;

import com.dal.hiree.entity.Job;
import com.dal.hiree.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDao {
    private DBHelper dbHelper;

    public JobDao() throws SQLException, ClassNotFoundException {
        dbHelper = DBHelper.getInstance();
    }

    public Job createJob(Job job) throws SQLException {
        String sqlQuery = "insert into Job(recruiter_id, job_headline, job_description, date_of_posting) values(%d, '%s', '%s', '%s')";
        String formattedQuery = String.format(sqlQuery, job.getRecruiterId(), job.getJobHeadline(), job.getJobDescription(), job.getDateOfPosting());
        dbHelper.executeCreateOrUpdateQuery(formattedQuery);
        String getSqlQuery = String.format("select id, recruiter_id, job_headline, job_description, date_of_posting, is_active from Job where id=%d order by created_at desc limit 1", job.getRecruiterId());
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int recruiterId = resultSet.getInt("recruiter_id");
            String jobHeadline = resultSet.getString("job_headline");
            String jobDescription = resultSet.getString("job_description");
            String dateOfPosting = resultSet.getString("date_of_posting");
            boolean isActive = resultSet.getBoolean("is_active");

            Job fecthedJob = new Job(id, recruiterId, jobHeadline, jobDescription, dateOfPosting, isActive);
            return fecthedJob;
        }
        throw new RuntimeException("Unknown error while creating job entry");
    }

    public Job updateJob(Job job) throws SQLException {
        if (job.getId() <= 0) {
            throw new RuntimeException("Id is not set");
        }
        String sqlQuery = "update Job set %s where id=%d";
        String subStr = "";
        subStr += String.format("is_active=%b", job.isActive());
        if (!job.getJobHeadline().isEmpty()) {
            subStr += String.format(", job_headline='%s'", job.getJobHeadline());
        }
        if (!job.getJobDescription().isEmpty()) {
            subStr += String.format(", job_description='%s'", job.getJobDescription());
        }
        if (!job.getDateOfPosting().isEmpty()) {
            subStr += String.format(", date_of_posting='%s'", job.getDateOfPosting());
        }
        String finalQuery = String.format(sqlQuery, subStr, job.getId());
        System.out.println(finalQuery);
        dbHelper.executeCreateOrUpdateQuery(finalQuery);
        return getJobById(job.getId());
    }

    public Job getJobById(int id) throws SQLException {
        String getSqlQuery = String.format("select id, recruiter_id, job_headline, job_description, date_of_posting, is_active from Job where id=%d", id);
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int _id = resultSet.getInt("id");
            int recruiterId = resultSet.getInt("recruiter_id");
            String jobHeadline = resultSet.getString("job_headline");
            String jobDescription = resultSet.getString("job_description");
            String dateOfPosting = resultSet.getString("date_of_posting");
            boolean isActive = resultSet.getBoolean("is_active");

            return new Job(_id, recruiterId, jobHeadline, jobDescription, dateOfPosting, isActive);
        }
        throw new RuntimeException("Unknown error");
    }

    public List<Job> getAllJob() throws SQLException {
        String getSqlQuery = "select id, recruiter_id, job_headline, job_description, date_of_posting, is_active from Job";
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        List<Job> jobList = new ArrayList<>();
        while (resultSet.next()) {
            int _id = resultSet.getInt("id");
            int recruiterId = resultSet.getInt("recruiter_id");
            String jobHeadline = resultSet.getString("job_headline");
            String jobDescription = resultSet.getString("job_description");
            String dateOfPosting = resultSet.getString("date_of_posting");
            boolean isActive = resultSet.getBoolean("is_active");
            jobList.add(new Job(_id, recruiterId, jobHeadline, jobDescription, dateOfPosting, isActive));
        }
        return jobList;
    }

    public void deleteJob(int id) throws SQLException {
        String sqlQuery = String.format("delete from job where id=%d", id);
        dbHelper.executeCreateOrUpdateQuery(sqlQuery);
    }
}
