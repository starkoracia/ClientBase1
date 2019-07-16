package application.sql.services.work;

import application.sql.entitys.work.Client;
import application.sql.interfaces.dao.DAO;
import application.sql.connectors.DBConnector;
import application.util.UserSetter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService extends DBConnector implements DAO<Client, Integer> {

    private String addSqlQuery = "insert into clients (name, mobile_number, email, find_recommendation, annotation) values (?,?,?,?,?);";
    private String getByKeySqlQuery = "select * from clients where id=?";
    private String getAllSqlQuery = "select * from clients";
    private String updateSqlQuery = "update clients set name=?, mobile_number=?, email=?, find_recommendation=?, annotation=? where id=?";
    private String deleteSqlQuery = "delete from clients where id=?";


    public ClientService() {
        super(UserSetter.getUser().getLogin());
    }

    @Override
    public void add(Client client) {
        if (!isConnect()) {
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(addSqlQuery);
             Statement statement = connection.createStatement()) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getMobileNumber());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getFindRecommendation());
            preparedStatement.setString(5, client.getAnnotation());
            preparedStatement.executeUpdate();

            ResultSet resultSet = statement.executeQuery("select last_insert_id() from clients");
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
            System.out.println("Added new Client!");
        } catch (SQLException e) {
            System.out.println("ERROR! Added new Client!");
            e.printStackTrace();
        }
    }

    @Override
    public Client getByKey(Integer id) {
        if (!isConnect()) {
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(getByKeySqlQuery)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                client.setId(resultSet.getInt(1));

                System.out.println("Received Client by key!");
                return client;
            }
            System.out.println("Client by key not found!");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client by key not found!");
            return null;
        }
    }

    @Override
    public List<Client> getAll() {
        if (!isConnect()) {
            toConnect();
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllSqlQuery);

            List<Client> clientList = new ArrayList<>();
            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                client.setId(resultSet.getInt(1));
                clientList.add(client);
            }
            System.out.println("Get all clients!");
            return clientList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR! Get all clients!");
            return null;
        }
    }

    @Override
    public void update(Client client) {
        if (!isConnect()) {
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSqlQuery)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getMobileNumber());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getFindRecommendation());
            preparedStatement.setString(5, client.getAnnotation());
            preparedStatement.setInt(6, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("Update client!");
        } catch (SQLException e) {
            System.out.println("ERROR! Update client!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Client client) {
        if(!isConnect()) {
            toConnect();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSqlQuery)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.executeUpdate();
            System.out.println("Deleted client!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
