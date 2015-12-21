package de.himbiss.quizme;



import com.cathive.fx.guice.GuiceFXMLLoader;
import de.himbiss.quizme.fxml_controller.QuizEvaluationController;
import de.himbiss.quizme.fxml_controller.TakeQuizController;
import de.himbiss.quizme.model.Answer;
import de.himbiss.quizme.model.Question;
import de.himbiss.quizme.model.Quiz;
import de.himbiss.quizme.model.QuizDAO;
import de.himbiss.quizme.util.Resources;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Vincent on 15.12.2015.
 */
public class QuizMe extends Application {

    private static final Logger logger = Logger.getLogger(QuizMe.class);
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("Starting Application");

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("QuizMe");
        this.primaryStage.getIcons().add(Resources.getInstance().getIcon());
        this.primaryStage.setOnCloseRequest(e -> shutdown() );

        Pane root = Resources.getInstance().loadFXML(Resources.MAIN_FXML).getRoot();
        Scene scene = new Scene(root);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public static void shutdown() {
        logger.info("Closing Application");
        QuizDAO.getInstance().shutdown();
        Platform.exit();
    }

    public static void startQuiz(Quiz quiz) {
        logger.info("Starting Quiz '" + quiz + "'");
        Stage stage = new Stage();
        stage.setTitle(quiz.getName());
        stage.getIcons().add(Resources.getInstance().getIcon());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getScene().getWindow());
        GuiceFXMLLoader.Result result = Resources.getInstance().loadFXML(Resources.TAKE_QUIZ_FXML);
        TakeQuizController takeQuizController = result.getController();
        takeQuizController.setQuiz(quiz);
        Scene scene = new Scene(result.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    public static void evaluateQuiz(Quiz quiz, Map<Question, List<Answer>> quizResults) {
        logger.info("Evaluating Quiz '" + quiz + "'");
        Stage stage = new Stage();
        stage.setTitle("Evaluation of " + quiz.getName());
        stage.getIcons().add(Resources.getInstance().getIcon());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getScene().getWindow());
        GuiceFXMLLoader.Result result = Resources.getInstance().loadFXML(Resources.QUIZ_EVALUATION_FXML);
        QuizEvaluationController quizEvaluationController = result.getController();
        quizEvaluationController.setQuizResults(quiz, quizResults);
        Scene scene = new Scene(result.getRoot());
        stage.setScene(scene);
        stage.show();
    }
}
