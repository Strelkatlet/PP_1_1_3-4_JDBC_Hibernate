package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String createUsersTable = "create table users(id BIGINT AUTO_INCREMENT primary key," +
            " name varchar(32)," +
            " lastName varchar(32)," +
            " age TINYINT)";
    private String dropUsersTable = "DROP TABLE users";
    private String saveUser = "insert into users VALUES(default,?,?,?)";
    private String removeUserById = "DELETE FROM users WHERE id = ";
    private String getAllUsers = "select * from users";
    private String cleanUsersTable = "delete from users";
    private PreparedStatement preparedstatement = null;
    private Statement statement = null;
    private Connection connection = null;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(createUsersTable);
            connection.commit();
            } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void dropUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute(dropUsersTable);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = Util.getConnection();
            User user = new User(name, lastName, age);
            PreparedStatement ps = connection.prepareStatement(saveUser, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void removeUserById(long id) {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute(removeUserById + id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllUsers);

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
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(cleanUsersTable);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
