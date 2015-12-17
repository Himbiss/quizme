package de.himbiss.quizme.model;

import de.himbiss.quizme.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vincent on 17.12.2015.
 */
public class QuizDAO {

    private static QuizDAO instance;
    private final Session session;

    private QuizDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public static QuizDAO getInstance() {
        if (instance == null)
            instance = new QuizDAO();
        return instance;
    }

    public List<Quiz> getAllQuizzes() {
        Query query = session.createQuery("From Quiz ");
        List<Quiz> resultList = query.list();
        return Collections.unmodifiableList(resultList);
    }

    public Quiz saveQuiz(Quiz quiz) {
        for (Question question : quiz.getQuestionList()) {
            persistObject(question);
        }
        return persistObject(quiz);
    }

    public Quiz createQuiz(String name) {
        return persistObject(new Quiz(name));
    }

    public Question createQuestion(Quiz quiz) {
        return persistObject(new Question(quiz));
    }

    private <T> T persistObject(T object) {
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        return object;
    }

    public void shutdown() {
        if (session.isConnected() && session.isOpen())
            session.disconnect();
    }
}
