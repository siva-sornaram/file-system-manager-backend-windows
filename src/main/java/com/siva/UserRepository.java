package com.siva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;


public class UserRepository {

    Connection con = null;
    public UserRepository() {
        try {
            con = DbConnection.getConnection();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectSql = "SELECT a.id, a.username, b.role FROM usernames a, roles b WHERE a.roleid = b.roleid;";
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql);
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setRole(rs.getString(3));

                users.add(user);

            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return users;
    }

    public User getUser(int id) {

        User user = new User();
        String selectSql = "SELECT a.id, a.username, b.role FROM usernames a, roles b WHERE a.id = ? and a.roleid = b.roleid;";
        try{
            PreparedStatement stmt = con.prepareStatement(selectSql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setRole(rs.getString(3));
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return user;

    }

    public boolean createUser(User user) {
        String createSql = "INSERT INTO usernames VALUES (null, ?, ?, ?)";
        try{
            PreparedStatement stmt = con.prepareStatement(createSql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(3, user.getRoleId());
            stmt.setString(2, SecureUtil.encode(user.getUserName()));
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        String updateSql = "UPDATE usernames SET username = ?, roleid = ? WHERE id = ?";
        try{
            PreparedStatement stmt = con.prepareStatement(updateSql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, user.getRoleId());
            stmt.setInt(3, user.getUserId());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        String deleteSql = "DELETE FROM usernames WHERE id = ?";
        try{
            PreparedStatement stmt = con.prepareStatement(deleteSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return false;
    }
    
}
