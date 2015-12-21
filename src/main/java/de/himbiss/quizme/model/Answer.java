package de.himbiss.quizme.model;

import de.himbiss.quizme.util.QuizMeProperties;
import javafx.beans.Observable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LongSummaryStatistics;

/**
 * Created by Vincent on 17.12.2015.
 */
@Entity
@Table(name = "Answer")
public class Answer implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Question question;

    @Column(name = "answer_answer")
    private String answer;

    @Column(name = "answer_is_true")
    private Boolean isTrue;

    Answer() {

    }

    public Answer(Question question, String answer, boolean isTrue) {
        this.question = question;
        this.answer = answer;
        this.isTrue = isTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer1 = (Answer) o;

        if (id != null ? !id.equals(answer1.id) : answer1.id != null) return false;
        if (question != null ? !question.equals(answer1.question) : answer1.question != null) return false;
        if (answer != null ? !answer.equals(answer1.answer) : answer1.answer != null) return false;
        return !(isTrue != null ? !isTrue.equals(answer1.isTrue) : answer1.isTrue != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (isTrue != null ? isTrue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (QuizMeProperties.getInstance().isDebugMode()) {
            return "Answer{" +
                    "id='" + id + "'" +
                    ", answer='" + answer + '\'' +
                    ", isTrue=" + isTrue +
                    '}';
        }
        else {
            return answer;
        }
    }

    public void setTrue(boolean isTrue) {
        this.isTrue = isTrue;
        QuizDAO.getInstance().saveQuiz(getQuestion().getQuiz());
    }
}
