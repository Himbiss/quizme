package de.himbiss.quizme.model;

import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    public Answer createAnswer(Question question) {
        logger.info("Creating new Answer on Question '" + question + "'");
        return persistObject(new Answer(question, "Answer", false));
    }

    public void deleteQuiz(Quiz quiz) {
        logger.info("Deleting Quiz '" + quiz + "'");
        quiz.getQuestionList().forEach(this::deleteQuestion);
        deleteObject(quiz);
    }

    public void deleteQuestion(Question question) {
        logger.info("Deleting Question '" + question + "'");
        question.getAnswers().forEach(this::deleteAnswer);
        deleteObject(question);
    }

    public void deleteAnswer(Answer answer) {
        logger.info("Deleting Answer '" + answer + "'");
        deleteObject(answer);
    }

    private void deleteObject(Object object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
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

    public boolean checkQuizExists(String quizName) {
        return getAllQuizzes().stream().filter( q -> q.getName().equals(quizName) ).findAny().isPresent();
    }

    public Quiz getQuiz(Quiz qui) {
        return entityManager.find(Quiz.class, qui.id);
    }
}
