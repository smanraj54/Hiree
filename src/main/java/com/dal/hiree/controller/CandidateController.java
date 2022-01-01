package com.dal.hiree.controller;

import com.dal.hiree.client.AWSS3SERVICE;
import com.dal.hiree.client.AWSSNSClient;
import com.dal.hiree.dao.CandidateDao;
import com.dal.hiree.dao.CandidateDetailsDao;
import com.dal.hiree.entity.AppliedJob;
import com.dal.hiree.entity.ApplyJobRequest;
import com.dal.hiree.entity.Candidate;
import com.dal.hiree.entity.CandidateDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    CandidateDao candidateDao;
    CandidateDetailsDao candidateDetailsDao;

    public CandidateController() {
        try {
            candidateDao = new CandidateDao();
            candidateDetailsDao = new CandidateDetailsDao();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "Pong";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createProfile(@RequestBody Candidate candidate) {
        try {
            Candidate createdCandidate = candidateDao.createCandidate(candidate);
            return getJson(createdCandidate);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while creating candidate object";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateProfile(@RequestBody Candidate candidate) {
        try {
            Candidate updatedCandidate = candidateDao.updateCandidate(candidate);
            return getJson(updatedCandidate);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while updating candidate object";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getProfiles() {
        List<Candidate> candidateList = null;
        try {
            candidateList = candidateDao.getAllCandidates();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(candidateList);
        } catch (SQLException | JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return "Unknown error while fetching all candidate list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getProfile(@PathVariable("id") int id) {
        System.out.printf("Fetching candidate for id: %d\n", id);
        try {
            Candidate candidate = candidateDao.getCandidateById(id);
            return getJson(candidate);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while getting candidate object";
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteProfile(@PathVariable("id") int id) {
        System.out.println("Candidate to be deleted with id: " + id);
        try {
            candidateDao.deleteCandidate(id);
            return "Successfully deleted the candidate";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Error while deleting the candidate";
        }
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createCandidateDetails(@RequestBody CandidateDetails candidateDetails) {
        try {
            CandidateDetails createdCandidateDetails = candidateDetailsDao.createCandidateDetails(candidateDetails);
            return getJson(createdCandidateDetails);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while creating candidateDetails object";
        }
    }

    @RequestMapping(value = "/details", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCandidateDetails(@RequestBody CandidateDetails candidateDetails) {
        try {
            CandidateDetails updatedCandidateDetails = candidateDetailsDao.updateCandidateDetails(candidateDetails);
            return getJson(updatedCandidateDetails);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while updating candidateDetails object";
        }
    }

    @RequestMapping(value = "/details/{candidate_id}", method = RequestMethod.GET)
    public String getCandidateDetails(@PathVariable("candidate_id") int candidate_id) {
        System.out.printf("Fetching candidateDetails for candidate_id: %d\n", candidate_id);
        try {
            CandidateDetails candidateDetails = candidateDetailsDao.getCandidateDetailsById(candidate_id);
            return getJson(candidateDetails);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while getting candidate details object";
        }
    }

    @RequestMapping(value = "/details/{candidate_id}", method = RequestMethod.DELETE)
    public String deleteCandidateDetails(@PathVariable("candidate_id") int candidate_id) {
        System.out.println("Candidate to be deleted with id: " + candidate_id);
        try {
            candidateDetailsDao.deleteCandidateDetails(candidate_id);
            return "Successfully deleted the candidateDetails";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Error while deleting the candidateDetails";
        }
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String applyJob(@RequestBody ApplyJobRequest jobRequest) {
        System.out.printf("Candidate: %d applying for job: %d", jobRequest.getCandidateId(), jobRequest.getJobId());
        try {
            AppliedJob appliedJob = candidateDao.applyForJob(jobRequest.getCandidateId(), jobRequest.getJobId());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(appliedJob);
        } catch (SQLException | JsonProcessingException exception) {
            exception.printStackTrace();
            return "Error while applying for job";
        }
    }

    @PostMapping(value = "/resume")
    public String uploadResume(@RequestPart(value = "file") MultipartFile file) {

        try {
            AWSS3SERVICE.getInstance().uploadObject(file);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "Error while applying for job";
        }

        return "Upload Successfull!!";
    }

    @RequestMapping(value = "/resume/{candidate_id}", method = RequestMethod.GET)
    public String getResume(@PathVariable("candidate_id") int candidate_id) {
        try {
            String fileName = "Manraj_Singh_Resume_Dalhousie Final.pdf";
            AWSS3SERVICE.getInstance().getObject(fileName);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "ERROR!! while downloading resume";
        }
        return "Downloading Successful!!!";
    }


    private String getJson(Candidate candidate) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(candidate);
    }

    private String getJson(CandidateDetails candidateDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(candidateDetails);
    }
}
