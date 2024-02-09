package org.example;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

public class Database {
    protected String dbHost = "localhost";
    protected String dbPort = "3306";
    protected String dbUser = "root";
    protected String dbPass = "1234";
    protected String dbName = "mydb";
    Connection dbConnection;
    {
        try {
            dbConnection = getDbConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public String getCar0() throws SQLException{
        String request = "SELECT * FROM cars WHERE id = 1";
        String name = null;
        try (PreparedStatement prSt = dbConnection.prepareStatement(request)) {
            ResultSet resultSet = prSt.executeQuery();

            while (resultSet.next()) {
                name = resultSet.getString("name");
            }
        }
        return name;
    }

    public Answer getCars(){
        String request = "SELECT * FROM cars";
        List<Car> carList = new java.util.ArrayList<>(Collections.emptyList());
        try (PreparedStatement prSt = dbConnection.prepareStatement(request)) {
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                String power = resultSet.getString("power");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                carList.add(new Car(id, name, type, brand, power, description, price));
                System.out.println(id + " " + name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return new Answer(carList);
    }

}