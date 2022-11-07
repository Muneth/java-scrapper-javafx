package com.example.scraping;


import com.example.scraping.scrol.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

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

    ArrayList<Scroll> scrollArrayList = new ArrayList<>();
    public ArrayList<Scroll> displaySearch() throws Exception {
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
                    scrollArrayList = discogsScroll.search(searchWord);
                    results = discogsScroll.search(inputModify(searchWord)).toString();
                    showresults.setText(results);
                } else if(fnac.isSelected()){
                    scrollArrayList = fnacScroll.search(inputModify(searchWord));
                    results = fnacScroll.search(inputModify(searchWord)).toString();
                    showresults.setText(results);
                } else if (vin.isSelected()){
                    scrollArrayList = vinylCornerScroll.search(searchWord);
                    results = vinylCornerScroll.search(searchWord).toString();
                    showresults.setText(results);
                } else if(leb.isSelected()) {
                    if(minValue.equals("")|| maxValue.equals("")){
                        minValue = "0";
                        maxValue = String.valueOf(Integer.MAX_VALUE);
                    }
                    scrollArrayList = leboncoinScroll.search(searchWord, Integer.parseInt(minValue), Integer.parseInt(maxValue));
                    results = leboncoinScroll.search (searchWord, Integer.parseInt(minValue), Integer.parseInt(maxValue)).toString();
                    showresults.setText(results);
                }else if(mes.isSelected()){
                    scrollArrayList = mesvinylesScroll.search(searchWord);
                    results = mesvinylesScroll.search(searchWord).toString();
                    showresults.setText(results);
                } else if (cul.isSelected()){
                    scrollArrayList = cultureFactoryScroll.search(inputModify(title));
                    results = cultureFactoryScroll.search(inputModify(title)).toString();
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
        return scrollArrayList;
    }

    @FXML
    public void makeTextFile() throws Exception {
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
        write = new PrintWriter(new BufferedWriter(new FileWriter(nomFichierSortie)));
        write.println(displaySearch());
        write.close();
    }

    @FXML
    private void askUserToSaveFile() throws FileNotFoundException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("save your results");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            PrintWriter writer = new PrintWriter(file);
            writer.println(showresults.getText());
            writer.close();
        }
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
        String res;
        res =  wordReplace.replace(" ", "+");
        return res;
    }

    public static String checkIfNull(String s) {
        if (s.equals("")) return "No Description";
        return s;
    }

    public void sendMailPopUp() throws Exception {
        makeTextFile();
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


    public void sendToDB() throws Exception {
        BddController bddController = new BddController();
        ArrayList<Scroll> scrollListToDb = displaySearch();
        for (Scroll scroll : scrollListToDb) {
            boolean answer = bddController.insertVinyles(scroll);
            if (answer) {
                System.out.println("Scroll is added successfully");
            } else {
                System.out.println("Something went wrong...");
            }
        }
    }
}