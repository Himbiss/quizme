package de.himbiss.quizme.fxml_controller;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.css.converters.StringConverter;
import de.himbiss.quizme.QuizMe;
import de.himbiss.quizme.model.Quiz;
import de.himbiss.quizme.model.QuizDAO;
import de.himbiss.quizme.util.QuizMeProperties;
import de.himbiss.quizme.util.Resources;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Vincent on 16.12.2015.
 */
public class QuizMeController implements Initializable {

    @FXML
    ListView<Quiz> quizListView;

    @FXML
    AnchorPane contentAnchorPane;

    ObservableList<Quiz> quizList;
    EditQuizController currentEditQuizController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizList = new ObservableListWrapper<>(new ArrayList<>(QuizDAO.getInstance().getAllQuizzes()));
        quizListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        quizListView.setOnMouseClicked( e -> handleEditQuiz());
        quizListView.setEditable(true);
        quizListView.setCellFactory(TextFieldListCell.forListView(new javafx.util.StringConverter<Quiz>() {
            private Quiz quiz;

            @Override
            public String toString(Quiz quiz) {
                this.quiz = quiz;
                return quiz.getName();
            }

            @Override
            public Quiz fromString(String string) {
                quiz.setName(string);
                return quiz;
            }
        }));
        quizListView.setItems(quizList);
        setWelcomeViewContent();
    }

    @FXML
    public void handleNewQuiz() throws IOException {
        Optional<String> result = Optional.empty();
        do {
            TextInputDialog dialog = new TextInputDialog("New Quiz");
            if (result.isPresent() && QuizDAO.getInstance().checkQuizExists(result.get())) {
                dialog.setHeaderText("Quiz already exists, please choose another name!");
            }
            else if (result.isPresent() && result.get().isEmpty()) {
                dialog.setHeaderText("The name can not be empty!");
            }
            dialog.setTitle("Create a new quiz");
            dialog.setContentText("Please enter the name:");
            result = dialog.showAndWait();

            if (result.equals(Optional.empty())) {
                return;
            }
        } while (!result.isPresent() || QuizDAO.getInstance().checkQuizExists(result.get()));

        Quiz quiz = QuizDAO.getInstance().createQuiz(result.get());
        quizList.add(quiz);
        quizListView.refresh();
    }

    @FXML
    public void handleEditQuiz() {
        Quiz quiz = quizListView.getSelectionModel().getSelectedItem();
        if (quiz != null) {
            GuiceFXMLLoader.Result result = Resources.getInstance().loadFXML(Resources.EDIT_QUIZ_FXML);
            if (result != null) {
                contentAnchorPane.getChildren().clear();
                Node root = result.getRoot();
                contentAnchorPane.getChildren().add(root);
                AnchorPane.setLeftAnchor(root, 0.);
                AnchorPane.setTopAnchor(root, 0.);
                AnchorPane.setRightAnchor(root, 0.);
                AnchorPane.setBottomAnchor(root, 0.);
                currentEditQuizController = result.getController();
                currentEditQuizController.setQuiz(quiz);
            }
        }
    }

    @FXML
    public void handleDeleteQuiz() {
        Quiz quiz = quizListView.getSelectionModel().getSelectedItem();
        if (quiz != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Quiz");
            alert.setContentText("Do you really want to delete the quiz '" + quiz.getName() + "'?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                QuizDAO.getInstance().deleteQuiz(quiz);
                quizList.remove(quiz);
                quizListView.refresh();
                if (currentEditQuizController != null && currentEditQuizController.getQuiz().equals(quiz)) {
                    setWelcomeViewContent();
                }
            }
        }
    }

    @FXML
    public void handleExit() {
        QuizMe.shutdown();
    }

    private void setWelcomeViewContent() {
        GuiceFXMLLoader.Result result = Resources.getInstance().loadFXML(Resources.WELCOME_FXML);
        contentAnchorPane.getChildren().clear();
        Node root = result.getRoot();
        contentAnchorPane.getChildren().add(root);
        AnchorPane.setLeftAnchor(root, 0.);
        AnchorPane.setTopAnchor(root, 0.);
        AnchorPane.setRightAnchor(root, 0.);
        AnchorPane.setBottomAnchor(root, 0.);
        currentEditQuizController = null;
    }

    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("QuizMe");
        alert.setTitle("About");
        alert.setGraphic(new ImageView(Resources.getInstance().getIcon()));
        alert.setContentText("QuizMe\n By Vincent Ortland\n vincent.ortland@gmx.de\n http://www.himbiss.de");
        alert.show();
    }
}
