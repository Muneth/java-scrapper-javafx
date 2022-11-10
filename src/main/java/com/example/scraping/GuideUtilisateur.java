package com.example.scraping;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The type Guide utilisateur.
 */
public class GuideUtilisateur {
    @FXML
    private Button fermerguide;

    /**
     * Close guide.
     */
    @FXML
    protected void closeGuide() {
        Stage stage = (Stage) fermerguide.getScene().getWindow();
        stage.close();
    }}
