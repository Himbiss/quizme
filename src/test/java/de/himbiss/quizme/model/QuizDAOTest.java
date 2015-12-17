package de.himbiss.quizme.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Vincent on 17.12.2015.
 */
public class QuizDAOTest {

    @Test
    public void testCreationOfQuiz() {
        Quiz quiz = QuizDAO.getInstance().createQuiz("testQuiz");
        assertThat(quiz.getQuestionList(), is(Collections.emptyList()));
        assertThat(quiz.getName(), is("testQuiz"));
    }

    @Test
    public void testRetrievalOfPersistentQuizList() {
        Quiz quiz = QuizDAO.getInstance().createQuiz("testQuiz");
        List<Quiz> quizzes = QuizDAO.getInstance().getAllQuizzes();
        assertThat(quizzes.size(), is(1));
        assertThat(quizzes.get(0), equalTo(quiz));
    }

    @Test
    public void testCreationOfQuestion() {
        Quiz quiz = QuizDAO.getInstance().createQuiz("testQuiz");
        assertThat(quiz.getQuestionList(), equalTo(Collections.emptyList()));
        assertThat(quiz.getName(), is("testQuiz"));

        Question question = QuizDAO.getInstance().createQuestion(quiz);
        assertThat(question.getAnswers(), equalTo(Collections.emptyList()));
        assertNull(question.getQuestion());
        question.setQuestion("Who am I?");
        question.addAnswer(new Answer(question, "A Human", true));
        question.addAnswer(new Answer(question, "A Computer", false));
        quiz.addQuestion(question);

        QuizDAO.getInstance().saveQuiz(quiz);
        quiz = QuizDAO.getInstance().getAllQuizzes().get(0);
        assertThat(quiz.getQuestionList().size(), is(1));
        question = quiz.getQuestionList().get(0);
        assertThat(question.getQuestion(), is("Who am I?"));
        assertThat(question.getAnswers(), equalTo(Arrays.asList("A Human", "A Computer")));
    }
}
