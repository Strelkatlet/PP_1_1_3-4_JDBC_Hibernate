package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final String createUsersTable = "create table users(id BIGINT AUTO_INCREMENT primary key," +
            " name varchar(32)," +
            " lastName varchar(32)," +
            " age TINYINT)";
    private final String dropUsersTable = "DROP TABLE users";
    private final String saveUser = "insert into users VALUES(default,?,?,?)";
    private final String removeUserById = "DELETE FROM users WHERE id = ";
    private final String getAllUsers = "select * from users";
    private final String cleanUsersTable = "delete from users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(createUsersTable);
            ps.execute(createUsersTable);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(dropUsersTable);
            ps.execute(dropUsersTable);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
//
    public void saveUser(String name, String lastName, byte age) {
        try (var connection = getConnection()) {
            User user = new User(name, lastName, age);

            PreparedStatement ps = connection.prepareStatement(saveUser, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//
    public void removeUserById(long id) {
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(removeUserById);
            ps.execute(removeUserById + id);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(getAllUsers);
            ResultSet resultSet = ps.executeQuery(getAllUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println(user);
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
//
    public void cleanUsersTable() {
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(cleanUsersTable);
            ps.execute(cleanUsersTable);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
