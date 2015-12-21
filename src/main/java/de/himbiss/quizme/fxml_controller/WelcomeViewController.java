package de.himbiss.quizme.fxml_controller;

import de.himbiss.quizme.util.QuizMeProperties;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vincent on 21.12.2015.
 */
public class WelcomeViewController implements Initializable {

    @FXML
    Label versionLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        versionLabel.setText("Version: " + QuizMeProperties.getInstance().getVersion());
    }
}
