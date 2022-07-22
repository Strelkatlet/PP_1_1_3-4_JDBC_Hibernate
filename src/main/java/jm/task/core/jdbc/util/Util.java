package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static SessionFactory sessionFactory;

//    public static Connection getConnection() throws SQLException {
//        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        connection.setAutoCommit(false);
//        return connection;
//    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty(Environment.URL, URL)
                        .setProperty(Environment.USER, USERNAME)
                        .setProperty(Environment.PASS, PASSWORD)
                        .addAnnotatedClass(User.class);
                ServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(registry);
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }
}
