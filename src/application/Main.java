package application;

import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import application.controllers.AuthenticateController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {

    private Stage primaryStage;

    private AnchorPane rootView;


    private String appName;

    private final String authenticateLocation = "view/Authenticate.fxml";
    private ResourceBundle resources;

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        showAuthenticateView();
    }

    private void showAuthenticateView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(authenticateLocation));
            rootView = loader.load();

            AuthenticateController controller = loader.getController();
            controller.setAuthenticateStage(primaryStage);

            primaryStage.setScene(new Scene(rootView));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}