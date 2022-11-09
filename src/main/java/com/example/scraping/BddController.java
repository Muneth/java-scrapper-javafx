package com.example.scraping;

import com.example.scraping.scrol.Scroll;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The type Bdd controller.
 */
public class BddController {
    @FXML
    private TextField bddserver, bddnom, bddport, bddlogin, bddpassword;
    @FXML
    private Label error;
    @FXML
    private Button bddvalider, bddfermer;
    /**
     * The Con.
     */
    Connection con;
    /**
     * The Err.
     */
    String err = "";
    private String serverbdd = "";
    private String nombdd = "";
    private String portbdd = "";
    private String loginbdd = "";
    private String passwordbdd = "";

    /**
     * Create c connection.
     *
     * @return the connection
     */
    public Connection createC(){
        try{
//      Load the driver
            Class.forName("com.mysql.jdbc.Driver");
//      Create the connection...
            try(BufferedReader br = new BufferedReader(new FileReader("util"+ File.separator +"bdddetalis.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append('\n');
                    line = br.readLine();
                }
                String everything = sb.toString();
                String[] bdddetail = everything.split("\n" );

                try {
                    serverbdd = bdddetail[0];
                    nombdd = bdddetail[1];
                    portbdd = bdddetail[2];
                    loginbdd = bdddetail[3];
                    passwordbdd = bdddetail[4];
                    if(serverbdd == null || nombdd == null || portbdd == null || loginbdd == null){
                        throw new IllegalArgumentException("BDD Details");
                    }
                    if(passwordbdd == null){
                        throw new IllegalArgumentException("password");
                    }
                } catch (IllegalArgumentException | IndexOutOfBoundsException e){
                    e.printStackTrace();
                    err = "Enter " + e.getMessage();
                    passwordbdd = "";
                }
            }
            String user = loginbdd;
            String password = passwordbdd;
            String url = "jdbc:mysql://"+serverbdd+":"+ portbdd +"/"+nombdd;

            con = DriverManager.getConnection(url,user,password);

        } catch (SQLException e){
            System.err.format("this is the error" + e.getSQLState() + e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Insert vinyles boolean.
     *
     * @param scroll the scroll
     * @return the boolean
     */
    public boolean insertVinyles(Scroll scroll){
        boolean f = false;

        try{
            System.out.println(serverbdd + nombdd + portbdd + loginbdd + passwordbdd);
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

    /**
     * Close page.
     */
    @FXML
    protected void closePage() {
        Stage stage = (Stage) bddfermer.getScene().getWindow();
        stage.close();
    }

    /**
     * Gets bdd details.
     *
     * @throws IOException the io exception
     */
    @FXML
    protected void getBddDetails() throws IOException {
        serverbdd = bddserver.getText();
        nombdd = bddnom.getText();
        portbdd = bddport.getText();
        loginbdd = bddlogin.getText();
        passwordbdd = bddpassword.getText();
        File rep = new File("util");
        rep.mkdir();
        String fileBdd = "util" + File.separator + "bdddetalis" + ".txt";
        PrintWriter write;
        write = new PrintWriter(new BufferedWriter(new FileWriter(fileBdd)));
        write.println(serverbdd + '\n' + nombdd + '\n' + portbdd + '\n' + loginbdd + '\n' + passwordbdd);
        write.close();
    }
}
