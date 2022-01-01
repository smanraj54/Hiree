package com.dal.hiree.dao;

import com.dal.hiree.entity.RecruiterDetails;
import com.dal.hiree.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecruiterDetailsDao {
    DBHelper dbHelper;
    public RecruiterDetailsDao() throws SQLException, ClassNotFoundException {
        dbHelper = DBHelper.getInstance();
    }

    public RecruiterDetails createRecruiterDetails(RecruiterDetails recruiterDetails) throws SQLException {
        String sqlQuery = "insert into RecruiterDetails(recruiter_id, DOJ, company_details) values(%d, '%s', '%s')";
        String formattedQuery = String.format(sqlQuery, recruiterDetails.getCompanyDetails(), recruiterDetails.getDOJ(), recruiterDetails.getCompanyDetails());
        dbHelper.executeCreateOrUpdateQuery(formattedQuery);
        String getSqlQuery = String.format("select id, recruiter_id, DOJ, company_details where recruiter_id=%d", recruiterDetails.getRecruiterId());
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int recruiter_id = resultSet.getInt("recruiter_id");
            String DOJ = resultSet.getString("DOJ");
            String company_details = resultSet.getString("company_details");
            return new RecruiterDetails(id, recruiter_id, DOJ, company_details);
        }
        throw new RuntimeException("Unknown error");
    }

    public RecruiterDetails updateRecruiterDetails(RecruiterDetails recruiterDetails) throws SQLException {
        if(recruiterDetails.getId() <= 0) {
            throw new RuntimeException("Id is not set");
        }
        String sqlQuery = "update RecruiterDetails set %s where id=%d";
        String subStr = "";
        if(!recruiterDetails.getDOJ().isEmpty()) {
            subStr += String.format("DOJ='%s'", recruiterDetails.getDOJ());
        }

        if(!recruiterDetails.getCompanyDetails().isEmpty()) {
            subStr += String.format(", company_details='%s'", recruiterDetails.getCompanyDetails());
        }
        if(subStr.isEmpty()) {
            throw new RuntimeException("Parameter is not set");
        }
        String finalQuery = String.format(sqlQuery, subStr, recruiterDetails.getId());
        System.out.println(finalQuery);
        dbHelper.executeCreateOrUpdateQuery(finalQuery);
        return getRecruiterDetailsById(recruiterDetails.getId());
    }

    public RecruiterDetails getRecruiterDetailsById(int id) throws SQLException {
        String getSqlQuery = String.format("select id, recruiter_id, DOJ, company_details from RecruiterDetails where id=%d", id);
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int idFetched = resultSet.getInt("id");
            int recruiterId = resultSet.getInt("recruiter_id");
            String DOJ = resultSet.getString("DOJ");
            String company_details = resultSet.getString("company_details");
            return new RecruiterDetails(idFetched, recruiterId, DOJ, company_details);
        }
        throw new RuntimeException("Unknown error");
    }

    public void deleteRecruiterDetails(int id) throws SQLException {
        String sqlQuery = String.format("delete from RecruiterDetails where id=%d", id);
        dbHelper.executeCreateOrUpdateQuery(sqlQuery);
    }
}
