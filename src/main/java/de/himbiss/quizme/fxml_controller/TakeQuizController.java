package de.himbiss.quizme.fxml_controller;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Singleton;
import de.himbiss.quizme.QuizMe;
import de.himbiss.quizme.model.Answer;
import de.himbiss.quizme.model.Question;
import de.himbiss.quizme.model.Quiz;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.*;

/**
 * Created by Vincent on 19.12.2015.
 */
@Singleton
@FXMLController(controllerId = "takeQuizController")
public class TakeQuizController implements Initializable {

    @FXML
    TextArea questionTextArea;

    @FXML
    GridPane answersGridPane;

    @FXML
    Label infoLabel;

    @FXML
    Button previousQuestionButton;

    @FXML
    Button nextQuestionButton;

    private Quiz quiz;
    private List<Question> questionList;
    private int questionIndex;
    private Map<Question, List<Answer>> quizResults = new HashMap<>();

    @FXML
    public void handlePrevious() {
        questionIndex--;
        if (questionIndex == 0) {
            previousQuestionButton.setDisable(true);
        }
        else if (questionIndex == questionList.size() - 1) {
            nextQuestionButton.setText("Next Question");
        }
        fillContent();
    }

    @FXML
    public void handleNext() {
        saveAnswer();
        questionIndex++;
        if (questionIndex == questionList.size() - 1) {
            nextQuestionButton.setText("Evaluate Quiz");
        }
        else if (questionIndex == questionList.size()) {
            QuizMe.evaluateQuiz(quiz, quizResults);
            Stage stage = (Stage) questionTextArea.getScene().getWindow();
            stage.close();
            return;
        }

        if (questionIndex > 0)
            previousQuestionButton.setDisable(false);

        fillContent();
    }

    private void saveAnswer() {
        if (questionIndex >= 0 && questionIndex < questionList.size()) {
            List<Answer> availableAnswers = questionList.get(questionIndex).getAnswers();
            List<Answer> answersSet = new ArrayList<>();
            List<Node> children = answersGridPane.getChildren();
            for (int i = 0; i < children.size(); i++) {
                CheckBox checkBox = (CheckBox) children.get(i);
                if (checkBox != null && checkBox.isSelected()) {
                    answersSet.add(availableAnswers.get(i));
                }
            }
            quizResults.put(questionList.get(questionIndex), answersSet);
        }
    }

    private void fillContent() {
        infoLabel.setText("Question " + (questionIndex + 1) + " of " + questionList.size());
        questionTextArea.setText(questionList.get(questionIndex).getQuestion());
        fillAnswerPane();
    }

    private void fillAnswerPane() {
        answersGridPane.getChildren().clear();
        List<Answer> answerList = questionList.get(questionIndex).getAnswers();
        for (int i = 0; i < answerList.size(); i++) {
            CheckBox checkBox = new CheckBox(answerList.get(i).getAnswer());
            answersGridPane.add(checkBox, i % 2, i / 2);
        }
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.questionList = new ArrayList<>(quiz.getQuestionList());
        Collections.shuffle(questionList);
        handleNext();
        previousQuestionButton.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionIndex = -1;
        quizResults = new HashMap<>();
    }
}
