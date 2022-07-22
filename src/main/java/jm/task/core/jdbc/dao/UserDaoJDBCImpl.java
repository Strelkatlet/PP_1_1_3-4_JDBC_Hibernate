package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final String CUT = "create table users(id BIGINT AUTO_INCREMENT primary key," +
            " name varchar(32)," +
            " lastName varchar(32)," +
            " age TINYINT)";
    private final String DUT = "DROP TABLE users";
    private final String SU = "insert into users VALUES(default,?,?,?)";
    private final String RUBI = "DELETE FROM users WHERE id = ";
    private final String GAU = "select * from users";
    private final String CLUT = "delete from users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CUT);
            ps.execute(CUT);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DUT);
            ps.execute(DUT);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
//
    public void saveUser(String name, String lastName, byte age) {
        try (var connection = getConnection()) {
            User user = new User(name, lastName, age);

            PreparedStatement ps = connection.prepareStatement(SU, Statement.RETURN_GENERATED_KEYS);
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
            PreparedStatement ps = connection.prepareStatement(RUBI);
            ps.execute(RUBI + id);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (var connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GAU);
            ResultSet resultSet = ps.executeQuery(GAU);

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
            PreparedStatement ps = connection.prepareStatement(CLUT);
            ps.execute(CLUT);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
