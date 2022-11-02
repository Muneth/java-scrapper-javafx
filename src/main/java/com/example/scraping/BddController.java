package com.example.scraping;

import com.example.scraping.scrol.Scrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BddController {
    static Connection con;
    public static Connection createC(){
        try{
//      Load the driver
            Class.forName("com.mysql.jdbc.Driver");

//      Create the connection...
            String user = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/vinyles";
            con = (Connection) DriverManager.getConnection(url,user,password);

        } catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

    public static boolean insertVinyles(Scrol scrol){
        boolean f = false;

        try{
//              JDBC CODE
            Connection con = BddController.createC();
            String q = "insert into vinylesss(title,description,prix,url) values(?,?,?,?)";

//              PreparedStatement
            PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(q);

//              SET the value of parameters
            preparedStatement.setString(1, scrol.getTitle());
            preparedStatement.setString(2, scrol.getDiscription());
            preparedStatement.setString(3, scrol.getPrice());
            preparedStatement.setString(4, scrol.getUrl());

//              EXECUTE
            preparedStatement.executeUpdate();
            f=true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}
