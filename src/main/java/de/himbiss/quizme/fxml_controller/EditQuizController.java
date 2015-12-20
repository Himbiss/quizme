package de.himbiss.quizme.fxml_controller;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Singleton;
import de.himbiss.quizme.model.Answer;
import de.himbiss.quizme.model.Question;
import de.himbiss.quizme.model.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Created by Vincent on 20.12.2015.
 */
@Singleton
@FXMLController(controllerId = "editQuizController")
public class EditQuizController {

    @FXML
    ListView<Question> questionListView;

    @FXML
    ListView<Answer> answerListView;

    private Quiz quiz;

    @FXML
    public void handleStartQuiz() {
        System.out.println("Starting Quiz " + quiz.getName());
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
