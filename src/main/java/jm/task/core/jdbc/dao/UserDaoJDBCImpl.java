package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final String CREATE_USERS_TABLE = "create table users(id BIGINT AUTO_INCREMENT primary key," +
            " name varchar(32)," +
            " lastName varchar(32)," +
            " age TINYINT)";
    private final String DROP_USERS_TABLE = "DROP TABLE users";
    private final String SAVE_USER = "insert into users VALUES(default,?,?,?)";
    private final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ";
    private final String GET_ALL_USERS = "select * from users";
    private final String CLEAN_USERS_TABLE = "delete from users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CREATE_USERS_TABLE);
            ps.execute(CREATE_USERS_TABLE);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DROP_USERS_TABLE);
            ps.execute(DROP_USERS_TABLE);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
//
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection()) {
            User user = new User(name, lastName, age);

            PreparedStatement ps = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS);
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
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(REMOVE_USER_BY_ID);
            ps.execute(REMOVE_USER_BY_ID + id);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = ps.executeQuery(GET_ALL_USERS);

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
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CLEAN_USERS_TABLE);
            ps.execute(CLEAN_USERS_TABLE);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
