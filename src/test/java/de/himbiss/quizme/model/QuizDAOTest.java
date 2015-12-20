package de.himbiss.quizme.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Vincent on 17.12.2015.
 */
public class QuizDAOTest {

    private Quiz quiz;

    @After
    public void clearDatabase() {
        if (quiz != null) {
            QuizDAO.getInstance().deleteQuiz(quiz);
        }
    }

    @Test
    public void testCreationOfQuiz() {
        quiz = QuizDAO.getInstance().createQuiz("testQuiz");
        assertEquals(new ArrayList<>(quiz.getQuestionList()), Collections.emptyList());
        assertThat(quiz.getName(), is("testQuiz"));
    }

    @Test
    public void testRetrievalOfPersistentQuizList() {
        quiz = QuizDAO.getInstance().createQuiz("testQuiz");
        List<Quiz> quizzes = QuizDAO.getInstance().getAllQuizzes();
        assertThat(quizzes.size(), is(1));
        assertThat(quizzes.get(0), equalTo(quiz));
    }

    @Test
    public void testCreationOfQuestion() {
        quiz = QuizDAO.getInstance().createQuiz("testQuiz");
        assertThat(new ArrayList<>(quiz.getQuestionList()), equalTo(Collections.emptyList()));
        assertThat(quiz.getName(), is("testQuiz"));

        Question question = QuizDAO.getInstance().createQuestion(quiz);
        assertThat(new ArrayList<>(question.getAnswers()), equalTo(Collections.emptyList()));
        assertNull(question.getQuestion());
        question.setQuestion("Who am I?");
        Answer answer1 = new Answer(question, "A Human", true);
        question.addAnswer(answer1);
        Answer answer2 = new Answer(question, "A Computer", false);
        question.addAnswer(answer2);
        quiz.addQuestion(question);

        QuizDAO.getInstance().saveQuiz(quiz);
        quiz = QuizDAO.getInstance().getAllQuizzes().get(0);
        assertThat(quiz.getQuestionList().size(), is(1));
        question = quiz.getQuestionList().get(0);
        assertThat(question.getQuestion(), is("Who am I?"));
        assertEquals(new ArrayList<>(question.getAnswers()), Arrays.asList(answer1, answer2));
    }
}
