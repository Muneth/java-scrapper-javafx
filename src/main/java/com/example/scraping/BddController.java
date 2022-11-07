package com.example.scraping;

import com.example.scraping.scrol.Scroll;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BddController {
    Connection con;
    public Connection createC(){
        try{
//      Load the driver
            Class.forName("com.mysql.jdbc.Driver");

//      Create the connection...
            String user = "";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/vinyles-test";

            con = DriverManager.getConnection(url,user,password);

        } catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

    public boolean insertVinyles(Scroll scroll){
        boolean f = false;

        try{
//              JDBC CODE
            Connection connection = this.createC();
            String q = "insert into vinylesss(title,genre,price,date,description,url) values(?,?,?,?,?,?)";

//              PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(q);

//              SET the value of parameters
            preparedStatement.setString(1, scroll.getTitle());
            preparedStatement.setString(2, scroll.getGenre());
            preparedStatement.setString(3, scroll.getPrice());
            preparedStatement.setString(4, scroll.getDate());
            preparedStatement.setString(5, scroll.getDescription());
            preparedStatement.setString(6, scroll.getUrl());

//              EXECUTE
            preparedStatement.executeUpdate();
            f=true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}
