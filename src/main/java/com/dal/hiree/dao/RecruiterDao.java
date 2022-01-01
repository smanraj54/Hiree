package com.dal.hiree.dao;

import com.dal.hiree.entity.Recruiter;
import com.dal.hiree.helper.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecruiterDao {
    DBHelper dbHelper;
    public RecruiterDao() throws SQLException, ClassNotFoundException {
        dbHelper = DBHelper.getInstance();
    }

    public Recruiter createRecruiter(Recruiter recruiter) throws SQLException {
        String sqlQuery = "insert into Recruiter(name, email, username, password) values('%s', '%s', '%s', '%s')";
        String formattedQuery = String.format(sqlQuery, recruiter.getName(), recruiter.getEmail(), recruiter.getUsername(), recruiter.getPassword());
        dbHelper.executeCreateOrUpdateQuery(formattedQuery);
        String getSqlQuery = String.format("select id, name, email, username, password, status from Recruiter where username='%s'", recruiter.getUsername());
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            boolean status = resultSet.getBoolean("status");

            Recruiter fecthedRecruiter = new Recruiter(id, name, email, username, password, status);
            return fecthedRecruiter;
        }
        throw new RuntimeException("Unknown error");
    }

    public Recruiter updateRecruiter(Recruiter recruiter) throws SQLException {
        if(recruiter.getId() <= 0) {
            throw new RuntimeException("Id is not set");
        }
        String sqlQuery = "update Recruiter set %s where id=%d";
        String subStr = "";
        subStr += String.format("status=%b", recruiter.isStatus());
        if(!recruiter.getName().isEmpty()) {
            subStr += String.format(", name='%s'", recruiter.getName());
        }
        if(!recruiter.getEmail().isEmpty()) {
            subStr += String.format(", email='%s'", recruiter.getEmail());
        }
        if(!recruiter.getPassword().isEmpty()) {
            subStr += String.format(", password='%s'", recruiter.getPassword());
        }
        String finalQuery = String.format(sqlQuery, subStr, recruiter.getId());
        System.out.println(finalQuery);
        dbHelper.executeCreateOrUpdateQuery(finalQuery);
        return getRecruiterById(recruiter.getId());
    }

    public Recruiter getRecruiterById(int id) throws SQLException {
        String getSqlQuery = String.format("select id, name, email, username, password, status from Recruiter where id=%d", id);
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        if (resultSet.next()) {
            int idFetched = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            boolean status = resultSet.getBoolean("status");

            return new Recruiter(idFetched, name, email, username, password, status);
        }
        throw new RuntimeException("Unknown error");
    }

    public List<Recruiter> getAllRecruiter() throws SQLException {
        String getSqlQuery = "select id, name, email, username, password, status from Recruiter";
        ResultSet resultSet = dbHelper.executeSelectQuery(getSqlQuery);
        List<Recruiter> recruiterList = new ArrayList<>();
        while (resultSet.next()) {
            int idFetched = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            boolean status = resultSet.getBoolean("status");
            recruiterList.add(new Recruiter(idFetched, name, email, username, password, status));
        }
        return recruiterList;
    }

    public void deleteRecruiter(int id) throws SQLException {
        String sqlQuery = String.format("delete from recruiter where id=%d", id);
        dbHelper.executeCreateOrUpdateQuery(sqlQuery);
    }
}
