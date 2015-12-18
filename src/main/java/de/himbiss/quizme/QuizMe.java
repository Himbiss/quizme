package de.himbiss.quizme;

/**
 * Created by Vincent on 15.12.2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URL;

public class QuizMe extends Application {

    private static final String MAIN_FXML = "fxml/QuizMe.fxml";
    private static final Logger logger = Logger.getLogger(QuizMe.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("QuizMe");
        URL resourceURL = getClass().getClassLoader().getResource(MAIN_FXML);
        if (resourceURL == null) {
            logger.fatal("Could not find main fxml file: " + MAIN_FXML);
            return;
        }
        Pane root = FXMLLoader.load(resourceURL);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
