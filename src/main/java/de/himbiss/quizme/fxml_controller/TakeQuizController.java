package de.himbiss.quizme.fxml_controller;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.himbiss.quizme.model.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * Created by Vincent on 19.12.2015.
 */
@Singleton
@FXMLController(controllerId = "takeQuizController")
public class TakeQuizController {

    @FXML
    TextArea questionTextArea;

    @FXML
    GridPane answersGridPane;

    private Quiz quiz;

    @FXML
    public void handlePrevious() {
        System.out.println("Previous " + quiz.getName());
    }

    @FXML
    public void handleNext() {
        System.out.println("Next");
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
