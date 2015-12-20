package de.himbiss.quizme;

/**
 * Created by Vincent on 15.12.2015.
 */

import de.himbiss.quizme.model.QuizDAO;
import de.himbiss.quizme.util.Resources;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jboss.logging.Logger;

import java.io.IOException;

public class QuizMe extends Application {

    private static final Logger logger = Logger.getLogger(QuizMe.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("Starting Application");

        primaryStage.setTitle("QuizMe");
        primaryStage.getIcons().add(new Image(QuizMe.class.getClassLoader().getResourceAsStream("img/icon.png")));
        primaryStage.setOnCloseRequest( e -> shutdown() );

        Pane root = Resources.getInstance().getFXML(Resources.MAIN_FXML);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void shutdown() {
        logger.info("Closing Application");
        QuizDAO.getInstance().shutdown();
        Platform.exit();
    }
}
