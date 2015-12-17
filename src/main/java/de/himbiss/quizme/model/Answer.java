package de.himbiss.quizme.model;

import javax.persistence.*;

/**
 * Created by Vincent on 17.12.2015.
 */
@Entity
@Table
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    private String answer;

    @ManyToOne
    private Question question;

    private boolean isTrue;

    public Answer(Question question, String answer, boolean isTrue) {
        this.question = question;
        this.answer = answer;
        this.isTrue = isTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isTrue() {
        return isTrue;
    }
}
