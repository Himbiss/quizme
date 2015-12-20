package de.himbiss.quizme.util;

import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vincent on 20.12.2015.
 */
public class QuizMeProperties {

    private static final Logger logger = Logger.getLogger(QuizMeProperties.class);
    private static final String PROPERTIES_FILE = "quizme.properties";
    private static QuizMeProperties instance;
    private final Properties properties;

    public static QuizMeProperties getInstance() {
        if (instance == null)
            instance = new QuizMeProperties();
        return instance;
    }

    private QuizMeProperties() {
        properties = new Properties();
        try {
            properties.load(QuizMeProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            logger.fatal("Failed to load properties file '" + PROPERTIES_FILE + "'", e);
            e.printStackTrace();
        }
    }

    public boolean isDebugMode() {
        return Boolean.valueOf(properties.getProperty("quizme.debugmode"));
    }


    public String getVersion() {
        return properties.getProperty("quizme.version");
    }
}
