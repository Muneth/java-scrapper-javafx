package com.example.scraping;


import com.example.scraping.scrol.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 * The type Vinyle controller.
 */
public class VinyleController {
    /**
     * The Discogs scroll.
     */
    DiscogsScroll discogsScroll = new DiscogsScroll();
    /**
     * The Fnac scroll.
     */
    FnacScroll fnacScroll = new FnacScroll();
    /**
     * The Vinyl corner scroll.
     */
    VinylCornerScroll vinylCornerScroll = new VinylCornerScroll();
    /**
     * The Leboncoin scroll.
     */
    LeboncoinScroll leboncoinScroll = new LeboncoinScroll();
    /**
     * The Mesvinyles scroll.
     */
    MesvinylesScroll mesvinylesScroll = new MesvinylesScroll();
    /**
     * The Culture factory scroll.
     */
    CultureFactoryScroll cultureFactoryScroll = new CultureFactoryScroll();
    /**
     * The Email.
     */
    Email email = new Email();
    @FXML
    private MenuItem quitter, saveastextfile, addbdd, basededonnes;
    @FXML
    private TextField titre,min, max;
    @FXML
    private ComboBox<String> genre;
    @FXML
    private  DatePicker date;
    @FXML
    private CheckBox dis, fnac, vin, leb, mes, cul;
    @FXML
    private Label error;
    @FXML
    private Button effacer, rechercher;
    @FXML
    private TextArea showresults;

    /**
     * Instantiates a new Vinyle controller.
     *
     * @throws IOException the io exception
     */
    public VinyleController() throws IOException {
    }

    /**
     * Close app.
     */
    @FXML
    protected void closeApp(){
        Platform.exit();
    }

    /**
     * Clear field.
     */
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

    /**
     * The Scroll array list.
     */
    ArrayList<Scroll> scrollArrayList = new ArrayList<>();

    /**
     * Display search array list.
     *
     * @return the array list
     * @throws Exception the exception
     */
    public ArrayList<Scroll> displaySearch() throws Exception {
        String err = "";
        String title = titre.getText();
        String type = genre.getValue();
        String year = "";
        double minValue = (double) 0;
        double maxValue = (double) 0;
        try {
            if(date.getValue() == null) {
                throw new NullPointerException();
            }
            year = String.valueOf(date.getValue().getYear());
        } catch (Exception e){
//            err = "Enter Date to filter search";
            year = "2022";
            e.printStackTrace();
        }

        try {
            minValue = Double.parseDouble(min.getText());
            maxValue = Double.parseDouble(max.getText());

        } catch (Exception e ){
//            err = "Enter a min-max to narrow the search";
            minValue = 0;
            maxValue = Integer.MAX_VALUE;
            e.printStackTrace();
        }
        String results = "";
        if(type.equals("Selectionnez un genre")){
            type = "";
        }
        String searchWord = (title+" "+type).trim();

        if(mes.isSelected() || dis.isSelected() || fnac.isSelected() || vin.isSelected() || leb.isSelected() || cul.isSelected()){
            if(title.equals("") && type.equals("")){
                err = "Enter or choose a search field";
            } else {
                if(dis.isSelected()){
                    scrollArrayList = discogsScroll.search(searchWord, minValue, maxValue);
                    results = discogsScroll.search(inputModify(searchWord), minValue, maxValue).toString();
                    showresults.setText(results);
                } else if(fnac.isSelected()){
                    scrollArrayList = fnacScroll.search(inputModify(searchWord), minValue, maxValue, year);
                    results = fnacScroll.search(inputModify(searchWord), minValue, maxValue, year).toString();
                    showresults.setText(results);
                } else if (vin.isSelected()){
                    scrollArrayList = vinylCornerScroll.search(inputModify(searchWord), minValue, maxValue, year);
                    results = vinylCornerScroll.search(inputModify(searchWord), minValue, maxValue,year).toString();
                    showresults.setText(results);
                } else if(leb.isSelected()) {
                    scrollArrayList = leboncoinScroll.search(searchWord, (int) minValue, (int) maxValue);
                    results = leboncoinScroll.search (searchWord, (int) minValue, (int) maxValue).toString();
                    showresults.setText(results);
                }else if(mes.isSelected()){
                    scrollArrayList = mesvinylesScroll.search(searchWord,  minValue, maxValue);
                    results = mesvinylesScroll.search(searchWord, minValue, maxValue).toString();
                    showresults.setText(results);
                } else if (cul.isSelected()){
                    scrollArrayList = cultureFactoryScroll.search(inputModify(title), minValue, maxValue);
                    results = cultureFactoryScroll.search(inputModify(title), minValue, maxValue).toString();
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

    /**
     * Make text file.
     *
     * @throws Exception the exception
     */
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

    /**
     * Convert to double double.
     *
     * @param price the price
     * @return the double
     */
    public static double convertToDouble(String price){
        String priceFinal = price.substring(0, price.indexOf(","));
       priceFinal =  priceFinal.replaceAll("[^0-9]", "");
       priceFinal = priceFinal.replaceAll("€", "");
//        priceFinal = priceFinal.replaceAll(",",".");
        return Double.parseDouble(priceFinal);
    }


    /**
     * Convert fnac double double.
     *
     * @param price the price
     * @return the double
     */
    public static double convertFnacDouble(String price){
        String priceFinal = price.trim();
        priceFinal = priceFinal.replaceAll("€", "");
        return Double.parseDouble(priceFinal);
    }

    /**
     * Get year string.
     *
     * @param inputDate the input date
     * @return the string
     */
    public static String getYear(String inputDate){
        return inputDate.substring(6);
    }

    /**
     * Fnac year string.
     *
     * @param inputDate the input date
     * @return the string
     */
    public static String fnacYear(String inputDate){
        return inputDate.replaceAll("[^0-9]", "");
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

    /**
     * Check if null string.
     *
     * @param s the s
     * @return the string
     */
    public static String checkIfNull(String s) {
        if (s.equals("")) return "No Value";
        return s;
    }

    /**
     * Send mail pop up.
     *
     * @throws Exception the exception
     */
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

    /**
     * Db scene stage.
     *
     * @return the stage
     * @throws IOException the io exception
     */
    public Stage DbScene() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(VinyleApplication.class.getResource("ihmBDD.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        Stage stage = new Stage();
        stage.setTitle("Parameters de la base de donnes");
        stage.setScene(scene);
        stage.show();
        return stage;
    }


    /**
     * Bdd validation pop up.
     */
    @FXML
    protected void bddValidationPopUp(){
        Stage popupwindow=new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Transmission BDD");
        Label label1= new Label("Transmission des donnees a la base de donnees");
        Label label2 = new Label("Cliquez sur Valider por lancer la transmission: ");
        Button button1=new Button("valider");
        button1.setOnAction(e -> {
            try {
                sendToDB();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        Button button2= new Button("fermer");
        button2.setOnAction(e -> popupwindow.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, label2,button1, button2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }


    /**
     * Send to db.
     *
     * @throws Exception the exception
     */
    public void sendToDB() throws Exception {
        BddController bddController = new BddController();
        ArrayList<Scroll> scrollListToDb = displaySearch();
        for (Scroll scroll : scrollListToDb) {
            boolean answer = bddController.insertVinyles(scroll);
            if (answer) {
                System.out.println("Scraps are added successfully");
            } else {
                System.out.println("Something went wrong while sending to DB...");
            }
        }
    }
}