package application.sql.services.authenticate;

import application.sql.entitys.authenticate.User;
import application.sql.interfaces.dao.DAO;
import application.sql.connectors.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService extends DBConnector implements DAO<User,String> {

    private static String dbName = "users_authentication";

    private String addSqlQuery = "insert into users (login, password) values (?,?)";
    private String getByKeySqlQuery = "select * from users where login=?";
    private String getAllSqlQuery = "select * from users";
    private String updateSqlQuery = "update users set password=? where login=?";
    private String deleteSqlQuery = "delete from users where login=?";


    public UserService() {
        super(dbName);
    }

    public void createDBSchemaForNewUser(User user) {
        if(!isConnect()){
            toConnect();
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("drop schema if exists %s",user.getLogin()));
            statement.executeUpdate(String.format("create schema if not exists %s",user.getLogin()));
            System.out.println("DB is created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(isConnect()){
                closeConnect();
            }
        }
    }

    @Override
    public void add(User user) {
        if(!isConnect()){
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(addSqlQuery);
             Statement statement = connection.createStatement()) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            ResultSet resultSet = statement.executeQuery("select last_insert_id() from users");
            if(resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            System.out.println("New user id added!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(isConnect()){
                closeConnect();
            }
        }

    }

    @Override
    public User getByKey(String login) {
        if(!isConnect()){
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(getByKeySqlQuery)) {
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                User user = new User(
                        resultSet.getString(2),
                        resultSet.getString(3));

                user.setId(resultSet.getInt("id"));
                System.out.println("Received user by key!");
                return user;
            } else {
                System.out.println("Not find user by key!");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(isConnect()){
                closeConnect();
            }
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        if(!isConnect()){
            toConnect();
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllSqlQuery);

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                user.setId(resultSet.getInt("id"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(isConnect()){
                closeConnect();
            }
        }
        System.out.println("Received userList!");
        return userList;
    }

    @Override
    public void update(User user) {
        if(!isConnect()){
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSqlQuery)) {

            preparedStatement.setString(1, user.getPassword());

            preparedStatement.executeUpdate();
            System.out.println("User is updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(isConnect()){
                closeConnect();
            }
        }
    }

    @Override
    public void delete(User user) {
        if(!isConnect()) {
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSqlQuery)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.executeUpdate();
            System.out.println("User is deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (isConnect()) {
                closeConnect();
            }
        }
    }
}