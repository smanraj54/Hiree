package com.dal.hiree.controller;

import com.dal.hiree.dao.RecruiterDao;
import com.dal.hiree.dao.RecruiterDetailsDao;
import com.dal.hiree.entity.Candidate;
import com.dal.hiree.entity.CandidateDetails;
import com.dal.hiree.entity.Recruiter;
import com.dal.hiree.entity.RecruiterDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    private RecruiterDao recruiterDao;
    private RecruiterDetailsDao recruiterDetailsDao;

    public RecruiterController() {
        try {
            recruiterDao = new RecruiterDao();
            recruiterDetailsDao = new RecruiterDetailsDao();
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
    public String createRecruiterProfile(@RequestBody Recruiter recruiter) {
        try {
            Recruiter createdRecruiter = recruiterDao.createRecruiter(recruiter);
            return getJson(createdRecruiter);
        } catch (SQLException | JsonProcessingException exception) {
            exception.printStackTrace();
            return "Error while creating recruiter object";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateProfile(@RequestBody Recruiter recruiter) {
        try {
            Recruiter updatedRecruited = recruiterDao.updateRecruiter(recruiter);
            return getJson(updatedRecruited);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while updating recruiter object";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getProfiles() {
        List<Recruiter> recruiterList = null;
        try {
            recruiterList = recruiterDao.getAllRecruiter();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(recruiterList);
        } catch (SQLException | JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return "Unknown error while fetching all recruiter list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getProfile(@PathVariable("id") int id) {
        System.out.printf("Fetching recruiter for id: %d\n", id);
        try {
            Recruiter recruiter = recruiterDao.getRecruiterById(id);
            return getJson(recruiter);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            return "Error while getting recruiter object";
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteProfile(@PathVariable("id") int id) {
        System.out.println("Recruiter to be deleted with id: " + id);
        try {
            recruiterDao.deleteRecruiter(id);
            return "Successfully deleted the recruiter";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error while deleting the recruiter";
        }
    }



    @RequestMapping(value = "/send_email", method = RequestMethod.POST)
    public boolean sendEmail() {
        return true;
    }

    private String getJson(Recruiter recruiter) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(recruiter);
    }

    private String getJson(RecruiterDetails recruiterDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(recruiterDetails);
    }
}
