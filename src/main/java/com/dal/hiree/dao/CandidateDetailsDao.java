package com.dal.hiree.dao;

import com.dal.hiree.entity.Candidate;
import com.dal.hiree.entity.CandidateDetails;
import com.dal.hiree.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateDetailsDao {
    DBHelper dbHelper;
    public CandidateDetailsDao() throws SQLException, ClassNotFoundException {
        dbHelper = DBHelper.getInstance();
    }

    public CandidateDetails createCandidateDetails(CandidateDetails candidateDetails) throws SQLException {
        String sqlQuery = "insert into CandidateDetails(candidate_id, DOB, about_me) values(%d, '%s', '%s')";
        String formattedQuery = String.format(sqlQuery, candidateDetails.getCandidateId(), candidateDetails.getDOB(), candidateDetails.getAboutMe());
        dbHelper.executeCreateOrUpdateQuery(formattedQuery);
        String getSqlQuery = String.format("select id, candidate_id, DOB, about_me where candidate_id=%d", candidateDetails.getCandidateId());
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int candidate_id = resultSet.getInt("candidate_id");
            String DOB = resultSet.getString("DOB");
            String about_me = resultSet.getString("about_me");
            return new CandidateDetails(id, candidate_id, DOB, about_me);
        }
        throw new RuntimeException("Unknown error");
    }

    public CandidateDetails updateCandidateDetails(CandidateDetails candidateDetails) throws SQLException {
        if(candidateDetails.getId() <= 0) {
            throw new RuntimeException("Id is not set");
        }
        String sqlQuery = "update CandidateDetails set %s where id=%d";
        String subStr = "";
        if(!candidateDetails.getDOB().isEmpty()) {
            subStr += String.format("DOB='%s'", candidateDetails.getDOB());
        }
        if(!candidateDetails.getAboutMe().isEmpty()) {
            subStr += String.format(", about_me='%s'", candidateDetails.getAboutMe());
        }
        if(subStr.isEmpty()) {
            throw new RuntimeException("Parameter is not set");
        }
        String finalQuery = String.format(sqlQuery, subStr, candidateDetails.getId());
        System.out.println(finalQuery);
        dbHelper.executeCreateOrUpdateQuery(finalQuery);
        return getCandidateDetailsById(candidateDetails.getId());
    }

    public CandidateDetails getCandidateDetailsById(int id) throws SQLException {
        String getSqlQuery = String.format("select id, candidate_id, DOB, about_me from CandidateDetails where id=%d", id);
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int idFetched = resultSet.getInt("id");
            int candidateId = resultSet.getInt("candidate_id");
            String DOB = resultSet.getString("DOB");
            String aboutMe = resultSet.getString("about_me");
            return new CandidateDetails(idFetched, candidateId, DOB, aboutMe);
        }
        throw new RuntimeException("Unknown error");
    }

    public void deleteCandidateDetails(int id) throws SQLException {
        String sqlQuery = String.format("delete from CandidateDetails where id=%d", id);
        dbHelper.executeCreateOrUpdateQuery(sqlQuery);
    }
}
