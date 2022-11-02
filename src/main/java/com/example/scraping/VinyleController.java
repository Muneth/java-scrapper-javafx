package com.example.scraping;


import com.example.scraping.scrol.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class VinyleController {

    Email email = new Email();
    Scrol1 scrol1 = new Scrol1();
    Scrol2 scrol2 = new Scrol2();
    Scrol3 scrol3 = new Scrol3();
    Scrol4 scrol4 = new Scrol4();
    @FXML
    private MenuItem quitter, addbdd;
    @FXML
    private TextField titre,min, max;
    @FXML
    private ComboBox<String> genre;
    @FXML
    private DatePicker date;
    @FXML
    private CheckBox dis, fnac, vin, leb, mes, cul;
    @FXML
    private Label error;
    @FXML
    private Button effacer, rechercher;
    @FXML
    private TextArea showresults;

    public VinyleController() throws IOException {
    }

    @FXML
    protected void closeApp(){
        Platform.exit();
    }
    @FXML
    public void clearField(){
        titre.setText("");
        // genre.setId("sugs");
        genre.setValue("Selectionnez un genre");
        date.setValue(null);
        min.setText("");
        max.setText("");
        dis.setSelected(false);
        fnac.setSelected(false);
        vin.setSelected(false);
        leb.setSelected(false);
        mes.setSelected(false);
        cul.setSelected(false);
        error.setText("");

    }
    public void displaySearch() throws Exception {
        String title = titre.getText();
        String type = genre.getValue();
        String time = String.valueOf(date.getValue());
        String minValue = min.getText();
        String maxValue = max.getText();
        String err = "";
        if(mes.isSelected() || dis.isSelected() || fnac.isSelected() || vin.isSelected() || leb.isSelected() || cul.isSelected()){
            if(title.equals("") && type.equals("Selectionnez un genre")){
                err = "Enter or choose a search field";
            } else {
                if(mes.isSelected()){
                    String searchWord = title+" "+type;
                    String resMes = String.valueOf(scrol2.search(searchWord));
                    showresults.setText(resMes);
                } else if (fnac.isSelected()){
                    try {
                        String resFnac = scrol3.search(type);
                        showresults.setText(resFnac);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else if (leb.isSelected()) {
                    if(minValue.equals("")|| maxValue.equals("")){
                        minValue = "0";
                        maxValue = String.valueOf(Integer.MAX_VALUE);
                     }
                    String resLebon = scrol1.search (title, Integer.parseInt(minValue), Integer.parseInt(maxValue));
                    showresults.setText(resLebon);
                } else if (cul.isSelected()){
                    String resCul = scrol4.search(titreModify());
                    showresults.setText(resCul);
                }
                else {
                    err = "Site not working choose another site";
                }
            }
        } else {
            err = "CHOOSE A SITE";
        }
        error.setText(err);
    }

    // Scroll 2 title modifier
    //
    private String toCapitalize(String str){
        if(titre.getText().equals("")){
            return str;
        } else {
            String[] words = str.split("\\s");
            StringBuilder capitalizeWord = new StringBuilder();
            for(String w:words){
                String first = w.substring(0,1);
                String afterFirst = w.substring(1);
                capitalizeWord.append(first.toUpperCase()).append(afterFirst).append(" ");
            }
            return capitalizeWord.toString().trim().replace(" ", "+");
        }
    }
    private String titreModify(){
        String title = titre.getText();
        String res = "";
        res =  title.replace(" ", "+");
        return res;
    }

    public void sendMailPopUp() throws IOException {
        Stage emailPopUp=new Stage();
        emailPopUp.initModality(Modality.APPLICATION_MODAL);
        emailPopUp.setTitle("Enovoi courriel");
        Label label1= new Label("Saisie dud courriel ");
        Label label2 = new Label("Veuillez saisir lémail de léxpediteur: ");
        TextField emailInput = new TextField();
        Button button2 = new Button("Envoyer");
        button2.setOnAction(e -> email.sendMail(emailInput.getText()));
        Button button1= new Button("Annuler");
        button1.setOnAction(e -> emailPopUp.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,label2,emailInput,button2,button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        emailPopUp.setScene(scene1);
        emailPopUp.showAndWait();
    }

    Leboncoin leboncoin = new Leboncoin();


    public void displaySear() throws Exception {
//        String res = scrol2.search(titre.getText());
//        showresults.setText(res);
//        try {
//        final List<Scrol> results  = leboncoin.search(String.valueOf(titre), 3);
//            for (Scrol result : results) {
//               // showresults.setText(String.valueOf(result));
//                System.out.println(result.toString());
//            }
//        } catch (Exception e) {
//            System.out.println("not working ");
//            throw new RuntimeException(e);
//        }
    }

    public void sendToDB() throws Exception {
//        final List<Scrol> results  = leboncoin.search(String.valueOf(titre), 3);
//        for (Scrol result : results){
//        boolean ans = BddController.insertVinyles(result);
//            if (ans) {
//                System.out.println("Student is added successfully");
//            } else {
//                System.out.println("Something went wrong...");
//            }
//        }
        error.setText("db class not working");
        System.out.println("sorry db class fucked up");
    }
}