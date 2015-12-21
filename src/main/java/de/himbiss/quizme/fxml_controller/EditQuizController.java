package de.himbiss.quizme.fxml_controller;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Singleton;
import com.sun.javafx.collections.ObservableListWrapper;
import de.himbiss.quizme.model.Answer;
import de.himbiss.quizme.model.Question;
import de.himbiss.quizme.model.Quiz;
import de.himbiss.quizme.model.QuizDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

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

    private String quizName;
    private ObservableList<Question> questionObservableList;
    private ObservableList<Answer> answerObservableList;
    private Question selectedQuestion;
    private Answer selectedAnswer;
    private Quiz quiz;

    public void refresh() {
        quiz = QuizDAO.getInstance().getQuiz(quizName);
        refreshQuestionsList();
        refreshAnswersList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionsListView.onMousePressedProperty().setValue(e -> handleQuestionClicked());
        answersListView.onMousePressedProperty().setValue(e -> handleAnswerClicked());
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
        System.out.println("Starting Quiz " + quiz.getName());
    }

    public void setQuiz(String quizName) {
        this.quizName = quizName;
        refresh();
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
