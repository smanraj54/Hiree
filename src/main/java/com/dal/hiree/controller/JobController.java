package com.dal.hiree.controller;

import com.dal.hiree.dao.JobDao;
import com.dal.hiree.entity.Job;
import com.dal.hiree.entity.Recruiter;
import com.dal.hiree.entity.RecruiterDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    private JobDao jobDao;

    public JobController() {
        try {
            jobDao = new JobDao();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String postJob(@RequestBody Job job) {
        try {
            Job createdJob = jobDao.createJob(job);
            return getJson(createdJob);
        } catch (SQLException | JsonProcessingException exception) {
            exception.printStackTrace();
            return "Error while creating job enrty";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateJob(@RequestBody Job job) {
        try {
            Job updatedJob = jobDao.updateJob(job);
            return getJson(updatedJob);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while updating job object";
        }
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public String getAllJobs() {
        List<Job> jobList = null;
        try {
            jobList = jobDao.getAllJob();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(jobList);
        } catch (SQLException | JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return "Unknown error while fetching all job list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getJob(@PathVariable("id") int id) {
        System.out.printf("Fetching job for id: %d\n", id);
        try {
            Job job = jobDao.getJobById(id);
            return getJson(job);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while getting job object";
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteJob(@PathVariable("id") int id) {
        System.out.println("Job to be deleted with id: " + id);
        try {
            jobDao.deleteJob(id);
            return "Successfully deleted the job";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error while deleting the job";
        }
    }

    private String getJson(Job job) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(job);
    }
}
