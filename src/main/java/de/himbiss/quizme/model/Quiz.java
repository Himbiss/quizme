package de.himbiss.quizme.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vincent on 17.12.2015.
 */
@Entity
@Table(name = "Quiz")
public class Quiz implements Serializable {

    @Column(name = "quiz_id")
    private Long id;

    @Id
    @Column(name = "quiz_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Question> questionList = new ArrayList<>();

    private Quiz() {
    }

    Quiz (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question question) {
        questionList.add(question);
    }

    public void removeQuestion(Question question) {
        questionList.remove(question);
    }

    public List<Question> getQuestionList() {
        return Collections.unmodifiableList(questionList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (id != null ? !id.equals(quiz.id) : quiz.id != null) return false;
        if (name != null ? !name.equals(quiz.name) : quiz.name != null) return false;
        return !(questionList != null ? !questionList.equals(quiz.questionList) : quiz.questionList != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (questionList != null ? questionList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
