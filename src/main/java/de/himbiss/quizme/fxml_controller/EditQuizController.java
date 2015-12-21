package de.himbiss.quizme.fxml_controller;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Singleton;
import com.sun.javafx.collections.ObservableListWrapper;
import de.himbiss.quizme.QuizMe;
import de.himbiss.quizme.model.Answer;
import de.himbiss.quizme.model.Question;
import de.himbiss.quizme.model.Quiz;
import de.himbiss.quizme.model.QuizDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Vincent on 20.12.2015.
 */
@Singleton
@FXMLController(controllerId = "editQuizController")
public class EditQuizController implements Initializable {

    @FXML
    ListView<Question> questionsListView;

    @FXML
    ListView<Answer> answersListView;

    private ObservableList<Question> questionObservableList;
    private ObservableList<Answer> answerObservableList;
    private Question selectedQuestion;
    private Answer selectedAnswer;
    private Quiz quiz;

    public void refresh() {
        quiz = QuizDAO.getInstance().getQuiz(quiz);
        refreshQuestionsList();
        refreshAnswersList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionsListView.setOnMouseClicked( e -> handleQuestionClicked());
        answersListView.onMousePressedProperty().setValue(e -> handleAnswerClicked());
        Callback<ListView<Answer>, ListCell<Answer>> cellFactory = CheckBoxListCell.forListView(answer -> {
            ObservableBooleanValue value = new SimpleBooleanProperty(answer.isTrue());
            value.addListener((observable, oldValue, newValue) -> {
                if (newValue == false && selectedQuestion.getAnswers().stream().mapToInt( a -> a.isTrue() ? 1 : 0).sum() == 1) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Error");
                    alert.setContentText("At least one answer has to be true!");
                    alert.show();
                    refresh();
                    return;
                }
                answer.setTrue(newValue);
            });
            return value;
        });
        answersListView.setCellFactory(cellFactory);
    }

    private void handleQuestionClicked() {
        Question question = questionsListView.getSelectionModel().getSelectedItem();
        if (question != null) {
            selectedQuestion = question;
            refresh();
        }
    }

    private void handleAnswerClicked() {
        Answer answer = answersListView.getSelectionModel().getSelectedItem();
        if (answer != null) {
            selectedAnswer = answer;
        }
    }

    private void refreshQuestionsList() {
        questionObservableList = new ObservableListWrapper<>(new ArrayList<>(quiz.getQuestionList()));
        questionsListView.setItems(questionObservableList);
        questionsListView.refresh();
    }

    private void refreshAnswersList() {
        if (! questionObservableList.isEmpty() && selectedQuestion != null) {
            answerObservableList = new ObservableListWrapper<>(new ArrayList<>(selectedQuestion.getAnswers()));
        }
        else {
            answerObservableList = new ObservableListWrapper<>(new ArrayList<>());
        }

        if (answerObservableList != null){
            answersListView.setItems(answerObservableList);
            answersListView.refresh();
        }
    }

    @FXML
    public void handleAddQuestion() {
        Question question = QuizDAO.getInstance().createQuestion(quiz);
        quiz.addQuestion(question);
        selectedQuestion = question;
        refresh();
    }

    @FXML
    public void handleAddAnswer() {
        if (selectedQuestion != null) {
            Answer answer = QuizDAO.getInstance().createAnswer(selectedQuestion);
            if (selectedQuestion.getAnswers().size() == 0)
                answer.setTrue(true);
            selectedQuestion.addAnswer(answer);
            refresh();
        }
    }

    @FXML
    public void handleRemoveQuestion() {
        if (quiz != null && selectedQuestion != null) {
            quiz.removeQuestion(selectedQuestion);
            selectedQuestion = null;
            refresh();
        }
    }

    @FXML
    public void handleRemoveAnswer() {
        if (selectedQuestion != null && selectedAnswer != null) {
            selectedQuestion.removeAnswer(selectedAnswer);
            selectedAnswer = null;
            refresh();
        }
    }

    @FXML
    public void handleStartQuiz() {
        QuizMe.startQuiz(quiz);
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        refresh();
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
