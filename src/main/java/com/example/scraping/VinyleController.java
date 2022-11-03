package com.example.scraping;


import com.example.scraping.scrol.*;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class VinyleController {
    DiscogsScroll discogsScroll = new DiscogsScroll();
    FnacScroll fnacScroll = new FnacScroll();
    VinylCornerScroll vinylCornerScroll = new VinylCornerScroll();
    LeboncoinScroll leboncoinScroll = new LeboncoinScroll();
    MesvinylesScroll mesvinylesScroll = new MesvinylesScroll();
    CultureFactoryScroll cultureFactoryScroll = new CultureFactoryScroll();
    Email email = new Email();
    @FXML
    private MenuItem quitter, saveastextfile, addbdd;
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
        showresults.setText("");
        error.setText("");

    }
    public String displaySearch() throws Exception {
        String title = titre.getText();
        String type = genre.getValue();
        String time = String.valueOf(date.getValue());
        String minValue = min.getText();
        String maxValue = max.getText();
        String results = "";
        String err = "";
        if(type.equals("Selectionnez un genre")){
            type = "";
        }
        String searchWord = (title+" "+type).trim();

        if(mes.isSelected() || dis.isSelected() || fnac.isSelected() || vin.isSelected() || leb.isSelected() || cul.isSelected()){
            if(title.equals("") && type.equals("")){
                err = "Enter or choose a search field";
            } else {
                if(dis.isSelected()){
                    results = discogsScroll.search(inputModify(searchWord));
                    showresults.setText(results);
                } else if(fnac.isSelected()){
                    results = fnacScroll.search(inputModify(searchWord));
                    showresults.setText(results);
                } else if (vin.isSelected()){
                    results = vinylCornerScroll.search(searchWord);
                    showresults.setText(results);
                } else if(leb.isSelected()) {
                    if(minValue.equals("")|| maxValue.equals("")){
                        minValue = "0";
                        maxValue = String.valueOf(Integer.MAX_VALUE);
                    }
                    results = leboncoinScroll.search (searchWord, Integer.parseInt(minValue), Integer.parseInt(maxValue));
                    showresults.setText(results);
                }else if(mes.isSelected()){
                    results = mesvinylesScroll.search(searchWord);
                    showresults.setText(results);
                } else if (cul.isSelected()){
                    results = cultureFactoryScroll.search(inputModify(title));
                    showresults.setText(results);
                }
                else {
                    err = "Site not working choose another site";
                }
            }
        } else {
            err = "CHOOSE A SITE";
        }
        error.setText(err);
        return results;
    }

    @FXML
    private void makeTextFile() throws Exception {
        String title = titre.getText();
        String type = genre.getValue();
        if(type.equals("Selectionnez un genre")){
            type = "";
        }
        String searchWord = (title+" "+type).trim();
        File rep = new File("util");
        rep.mkdir();
        String nomFichierSortie = "util" + File.separator + searchWord + ".txt";
        PrintWriter write;
        write = new PrintWriter(new BufferedWriter
                (new FileWriter(nomFichierSortie)));
        write.println(displaySearch());
        write.close();
    }

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
    private String inputModify(String wordReplace){
        String res = "";
        res =  wordReplace.replace(" ", "+");
        return res;
    }

    public static String checkDescription(final HtmlElement e) {
        if (e == null) return "Null";
        return e.getTextContent();
    }

    public void sendMailPopUp() throws IOException {
        Stage emailPopUp=new Stage();
        emailPopUp.initModality(Modality.APPLICATION_MODAL);
        emailPopUp.setTitle("Enovoi courriel");
        Label label1= new Label("Saisie dud courriel ");
        Label label2 = new Label("Veuillez saisir lémail de léxpediteur: ");
        TextField emailInput = new TextField();
        Button button2 = new Button("Envoyer");
        button2.setOnAction(e -> email.sendMail(emailInput.getText(), titre.getText()));
        Button button1= new Button("Annuler");
        button1.setOnAction(e -> emailPopUp.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,label2,emailInput,button2,button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        emailPopUp.setScene(scene1);
        emailPopUp.showAndWait();
    }

    CrawlReturnScrollTest crawlReturnScrollTest = new CrawlReturnScrollTest();
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
        System.out.println("db not working");
    }
}