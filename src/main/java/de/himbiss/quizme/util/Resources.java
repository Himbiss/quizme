package de.himbiss.quizme.util;

import javafx.fxml.FXMLLoader;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 19.12.2015.
 */
public class Resources {

    public static final String MAIN_FXML = "/fxml/QuizMe.fxml";
    public static final String QUIZ_FXML = "/fxml/Quiz.fxml";
    private static Resources instance;

    private final Logger logger = Logger.getLogger(Resources.class);
    private Map<String, Object> loadedFXMLResources = new HashMap<>();

    private Resources() {
        preloadFXMLFiles();
    }

    public static Resources getInstance() {
        if (instance == null)
            instance = new Resources();
        return instance;
    }

    public <T> T getFXML(String fxmlPath) {
        return (T) loadedFXMLResources.get(fxmlPath);
    }

    private <T> T loadFXML(String fxmlPath) throws IOException {
        URL url = getClass().getResource(fxmlPath);
        if (url == null) {
            logger.fatal("Could not find fxml file: " + fxmlPath);
        }
        return FXMLLoader.load(url);
    }

    private void preloadFXMLFiles() {
        try {
            loadedFXMLResources.put(MAIN_FXML, loadFXML(MAIN_FXML));
            loadedFXMLResources.put(QUIZ_FXML, loadFXML(QUIZ_FXML));
        }
        catch (IOException ioe) {
            logger.fatal("Error preloading FXML files: ", ioe);
        }
    }
}
