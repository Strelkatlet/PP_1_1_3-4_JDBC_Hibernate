package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final String CREATEUSERSTABLE = "create table users(id BIGINT AUTO_INCREMENT primary key," +
            " name varchar(32)," +
            " lastName varchar(32)," +
            " age TINYINT)";
    private final String DROPUSERSTABLE = "DROP TABLE users";
    private final String SAVEUSER = "insert into users VALUES(default,?,?,?)";
    private final String REMOVEUSERBYID = "DELETE FROM users WHERE id = ";
    private final String GETALLUSERS = "select * from users";
    private final String CLEANUSERSTABLE = "delete from users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(CREATEUSERSTABLE);
            ps.execute(CREATEUSERSTABLE);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DROPUSERSTABLE);
            ps.execute(DROPUSERSTABLE);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
//
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection()) {
            User user = new User(name, lastName, age);

            PreparedStatement ps = connection.prepareStatement(SAVEUSER, Statement.RETURN_GENERATED_KEYS);
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
            PreparedStatement ps = connection.prepareStatement(REMOVEUSERBYID);
            ps.execute(REMOVEUSERBYID + id);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
//
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GETALLUSERS);
            ResultSet resultSet = ps.executeQuery(GETALLUSERS);

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
            PreparedStatement ps = connection.prepareStatement(CLEANUSERSTABLE);
            ps.execute(CLEANUSERSTABLE);

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
