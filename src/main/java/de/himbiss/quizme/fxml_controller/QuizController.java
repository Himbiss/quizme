package de.himbiss.quizme.fxml_controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * Created by Vincent on 19.12.2015.
 */
public class QuizController {

    @FXML
    TextArea questionTextArea;

    @FXML
    GridPane answersGridPane;

    @FXML
    public void handlePrevious() {
        System.out.println("Previous");
    }

    @FXML
    public void handleNext() {
        System.out.println("Next");
    }
}
