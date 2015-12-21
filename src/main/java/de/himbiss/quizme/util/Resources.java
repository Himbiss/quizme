package de.himbiss.quizme.util;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Vincent on 19.12.2015.
 */
public class Resources {

    public static final String MAIN_FXML = "/fxml/QuizMe.fxml";
    public static final String TAKE_QUIZ_FXML = "/fxml/TakeQuiz.fxml";
    public static final String EDIT_QUIZ_FXML = "/fxml/EditQuiz.fxml";
    public static final String WELCOME_FXML = "/fxml/WelcomeView.fxml";

    private final Injector injector = Guice.createInjector(new GuiceModule());
    private static Resources instance;

    private final Logger logger = Logger.getLogger(Resources.class);
    private final GuiceFXMLLoader loader = new GuiceFXMLLoader(injector, GuiceModule.fxmlLoadingScope);

    private Resources() {

    }

    public static Resources getInstance() {
        if (instance == null)
            instance = new Resources();
        return instance;
    }

    public GuiceFXMLLoader.Result loadFXML(String fxmlPath) {
        URL url = loadFXMLFile(fxmlPath);
        try {
            return loader.load(url);
        } catch (IOException e) {
            logger.fatal("Error loading fxml file: " + fxmlPath, e);
            e.printStackTrace();
        }
        return null;
    }

    private URL loadFXMLFile(String fxmlPath) {
        URL url = getClass().getResource(fxmlPath);
        if (url == null) {
            logger.fatal("Could not find fxml file: " + fxmlPath);
        }
        return url;
    }

}
