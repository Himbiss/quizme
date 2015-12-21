package de.himbiss.quizme.fxml_controller;

import de.himbiss.quizme.model.Answer;
import de.himbiss.quizme.model.Question;
import de.himbiss.quizme.model.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Vincent on 21.12.2015.
 */
public class QuizEvaluationController {

    @FXML
    PieChart evaluationChart;

    @FXML
    Label headerLabel;

    @FXML
    Label evaluationLabel;

    private Quiz quiz;
    private Map<Question, List<Answer>> answerMap;

    private void fillContent() {
        headerLabel.setText("Evaluation of Quiz '" + quiz + "'");
        int result = evaluate();
        double percentageRight = ( (double)result / (double)quiz.getQuestionList().size() ) * 100.;
        evaluationLabel.setText("You answered " + result + " out of " + quiz.getQuestionList().size() + " Questions right! (" + percentageRight + "%)");
        int unanswered = unanswered();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Right Questions", result),
                        new PieChart.Data("Wrong Questions", quiz.getQuestionList().size() - unanswered - result),
                        new PieChart.Data("Unanswered Questions", unanswered));
        evaluationChart.setData(pieChartData);
        applyCustomColorSequence(pieChartData, "green", "red", "grey");
    }

    private int unanswered() {
        return answerMap.values().stream().mapToInt( l -> l.isEmpty() ? 1 : 0 ).sum();
    }

    public void setQuizResults(Quiz quiz, Map<Question, List<Answer>> answerMap) {
        this.quiz = quiz;
        this.answerMap = answerMap;
        fillContent();
    }

    private void applyCustomColorSequence(
            ObservableList<PieChart.Data> pieChartData,
            String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle(
                    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
            );
            i++;
        }
    }

    private int evaluate() {
        int rightAnswerCount = 0;
        for (Map.Entry<Question, List<Answer>> entry : answerMap.entrySet()) {
            List<Answer> rightAnswers = entry.getKey().getAnswers().stream().filter(Answer::isTrue).collect(Collectors.toList());
            List<Answer> givenAnswers = entry.getValue();
            if (! givenAnswers.isEmpty() && compareAnswers(rightAnswers, givenAnswers)) {
                rightAnswerCount++;
            }
        }
        return  rightAnswerCount;
    }

    private boolean compareAnswers(List<Answer> rightAnswers, List<Answer> givenAnswers) {
        return rightAnswers.containsAll(givenAnswers) && givenAnswers.containsAll(rightAnswers);
    }

}
