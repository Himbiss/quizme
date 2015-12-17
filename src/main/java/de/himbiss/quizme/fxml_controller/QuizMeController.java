package de.himbiss.quizme.fxml_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Created by Vincent on 16.12.2015.
 */
public class QuizMeController {

    @FXML
    TextArea textArea;

    @FXML
    Label label;

    @FXML
    protected void buttonPressed(){
        String text = textArea.getText();
        label.setText(text);
        textArea.clear();
    }

}
