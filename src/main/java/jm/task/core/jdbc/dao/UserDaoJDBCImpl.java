package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = super.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table Users
                    (
                    id int primary key auto_increment not null,
                    name varchar(50),
                    lastName varchar(50),
                    age int
                    )
                    """);
        } catch (SQLException ignore) {
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE Users;");
        } catch (SQLException ignore) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "insert into Users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
        } catch (SQLException ignore) {
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "delete from Users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sqlQuery = "select * from Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            List<User> list = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        (byte) resultSet.getInt("age")));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        String sqlQuery = "delete from Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
