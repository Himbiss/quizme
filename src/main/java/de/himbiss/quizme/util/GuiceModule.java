package de.himbiss.quizme.util;

import com.cathive.fx.guice.FXMLController;
import com.cathive.fx.guice.fxml.FXMLLoadingScope;
import com.google.inject.AbstractModule;
import de.himbiss.quizme.fxml_controller.EditQuizController;
import de.himbiss.quizme.fxml_controller.TakeQuizController;

/**
 * Created by Vincent on 20.12.2015.
 */
public class GuiceModule extends AbstractModule {

    public static FXMLLoadingScope fxmlLoadingScope = new FXMLLoadingScope();

    @Override
    protected void configure() {
        bindScope(FXMLController.class, fxmlLoadingScope);
        bind(TakeQuizController.class).asEagerSingleton();
        bind(EditQuizController.class).asEagerSingleton();
    }
}
