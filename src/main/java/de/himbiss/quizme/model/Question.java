package de.himbiss.quizme.model;

import de.himbiss.quizme.util.QuizMeProperties;
import org.omg.CORBA.Any;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vincent on 17.12.2015.
 */
@Entity
@Table(name = "Question")
public class Question implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question_name")
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Answer> answers = new ArrayList<>();

    @ManyToOne
    private Quiz quiz;

    Question() {

    }

    public Question(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        if (id != null ? !id.equals(question1.id) : question1.id != null) return false;
        if (question != null ? !question.equals(question1.question) : question1.question != null) return false;
        if (answers != null ? !answers.equals(question1.answers) : question1.answers != null) return false;
        return !(quiz != null ? !quiz.equals(question1.quiz) : question1.quiz != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (quiz != null ? quiz.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (QuizMeProperties.getInstance().isDebugMode()) {
            return "Question{" +
                    "id=" + id +
                    ", question='" + question + '\'' +
                    ", quiz=" + quiz +
                    '}';
        }
        else {
            return question;
        }
    }
}
