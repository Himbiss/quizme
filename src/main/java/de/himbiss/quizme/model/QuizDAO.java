package de.himbiss.quizme.model;

import com.sun.javafx.runtime.SystemProperties;
import javafx.application.Application;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.jboss.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vincent on 17.12.2015.
 */
public class QuizDAO {
    private final EntityManagerFactory entityManagerFactory;
    private Logger logger = Logger.getLogger(QuizDAO.class);

    private static QuizDAO instance;
    private final EntityManager entityManager;

    private QuizDAO() {
        PersistenceProvider persistenceProvider = new HibernatePersistenceProvider();
        entityManagerFactory = persistenceProvider.createEntityManagerFactory("persistence", new HashMap<>());
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static QuizDAO getInstance() {
        if (instance == null)
            instance = new QuizDAO();
        return instance;
    }

    public List<Quiz> getAllQuizzes() {
        javax.persistence.Query query = entityManager.createQuery("SELECT e FROM Quiz e");
        List<Quiz> resultList = query.getResultList();
        return Collections.unmodifiableList(resultList);
    }

    public Quiz saveQuiz(Quiz quiz) {
        logger.info("Saving Quiz '" + quiz + "'");
        /*for (Question question : quiz.getQuestionList()) {
            question.getAnswers().forEach(this::persistObject);
            persistObject(question);
        }*/
        return persistObject(quiz);
    }

    public Quiz createQuiz(String name) {
        logger.info("Creating new Quiz '" + name + "'");
        return persistObject(new Quiz(name));
    }

    public Question createQuestion(Quiz quiz) {
        logger.info("Creating new Question on Quiz '" + quiz + "'");
        return persistObject(new Question(quiz));
    }

    private <T> T persistObject(T object) {
        logger.info("Persisting Object '" + object + "'");
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        return object;
    }

    public void shutdown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public void deleteQuiz(Quiz quiz) {
        logger.info("Deleting Quiz '" + quiz + "'");
        entityManager.getTransaction().begin();
        entityManager.remove(quiz);
        entityManager.getTransaction().commit();
    }

    public boolean checkQuizExists(String quizName) {
        return getAllQuizzes().stream().filter( q -> q.getName().equals(quizName) ).findAny().isPresent();
    }
}
