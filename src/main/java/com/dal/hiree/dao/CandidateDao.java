package com.dal.hiree.dao;

import com.dal.hiree.entity.AppliedJob;
import com.dal.hiree.entity.Candidate;
import com.dal.hiree.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidateDao {
    DBHelper dbHelper;
    public CandidateDao() throws SQLException, ClassNotFoundException {
        dbHelper = DBHelper.getInstance();
    }

    public Candidate createCandidate(Candidate candidate) throws SQLException {
        String sqlQuery = "insert into Candidate(name, email, username, password) values('%s', '%s', '%s', '%s')";
        String formattedQuery = String.format(sqlQuery, candidate.getName(), candidate.getEmail(), candidate.getUsername(), candidate.getPassword());
        dbHelper.executeCreateOrUpdateQuery(formattedQuery);
        String getSqlQuery = String.format("select id, name, email, username, password, status from Candidate where username='%s'", candidate.getUsername());
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            boolean status = resultSet.getBoolean("status");

            return new Candidate(id, name, email, username, password, status);
        }
        throw new RuntimeException("Unknown error");
    }

    public Candidate updateCandidate(Candidate candidate) throws SQLException {
        if(candidate.getId() <= 0) {
            throw new RuntimeException("Id is not set");
        }
        String sqlQuery = "update Candidate set %s where id=%d";
        String subStr = "";
        subStr += String.format("status=%b", candidate.isStatus());
        if(!candidate.getName().isEmpty()) {
            subStr += String.format(", name='%s'", candidate.getName());
        }
        if(!candidate.getEmail().isEmpty()) {
            subStr += String.format(", email='%s'", candidate.getEmail());
        }
        if(!candidate.getPassword().isEmpty()) {
            subStr += String.format(", password='%s'", candidate.getPassword());
        }
        String finalQuery = String.format(sqlQuery, subStr, candidate.getId());
        System.out.println(finalQuery);
        dbHelper.executeCreateOrUpdateQuery(finalQuery);
        return getCandidateById(candidate.getId());
    }

    public Candidate getCandidateById(int id) throws SQLException {
        String getSqlQuery = String.format("select id, name, email, username, password, status from Candidate where id=%d", id);
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int idFetched = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            boolean status = resultSet.getBoolean("status");

            return new Candidate(idFetched, name, email, username, password, status);
        }
        throw new RuntimeException("Unknown error");
    }

    public List<Candidate> getAllCandidates() throws SQLException {
        String getSqlQuery = "select id, name, email, username, password, status from Candidate";
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        List<Candidate> candidateList = new ArrayList<>();
        while (resultSet.next()) {
            int idFetched = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            boolean status = resultSet.getBoolean("status");
            candidateList.add(new Candidate(idFetched, name, email, username, password, status));
        }
        resultSet.close();
        return candidateList;
    }

    public void deleteCandidate(int id) throws SQLException {
        String sqlQuery = String.format("delete from Candidate where id=%d", id);
        dbHelper.executeCreateOrUpdateQuery(sqlQuery);
    }

    public AppliedJob applyForJob(int candidateId, int jobId) throws SQLException {
        String query = String.format("insert into AppliedJob(job_id, candidate_id) values(%d, %d)", jobId, candidateId);
        dbHelper.executeCreateOrUpdateQuery(query);
        String selectQuery = String.format("select id, job_id, candidate_id, date_of_application, result from AppliedJob where job_id=%d and candidate_id=%d", jobId, candidateId);
        System.out.println("Query: " + selectQuery);
        ResultSet resultSet = dbHelper.executeSelectQuery(selectQuery);
        if (resultSet.next()) {
            System.out.println("Fetching all columns value of table: AppliedJob");
            int id = resultSet.getInt("id");
            int _jobId = resultSet.getInt("job_id");
            int _candidateId = resultSet.getInt("candidate_id");
            String dateOfApplication = resultSet.getDate("date_of_application").toString();
            String result = resultSet.getString("result");
            return new AppliedJob(id, _jobId, _candidateId, dateOfApplication, result);
        }
        throw new RuntimeException("Unknown error while fetching entry from AppliedJob table");
    }
}
