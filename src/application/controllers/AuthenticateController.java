package application.controllers;

import application.sql.entitys.authenticate.User;
import application.sql.services.authenticate.UserService;
import application.sql.connectors.SessionConnector;
import application.util.AlertCaster;
import application.util.ClearedButtonSetter;
import application.sql.HibernateSessionFactory;
import application.util.UserSetter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class AuthenticateController implements Initializable {
    @FXML
    public CustomTextField loginTextField;
    @FXML
    public CustomPasswordField passwordTextField;
    @FXML
    public Button enterButton;
    @FXML
    public Button registrationButton;

    private String registrationViewLocation = "../view/Registration.fxml";
    private String rootViewLocation = "../view/RootView.fxml";
    private Stage authenticateStage;
    private Stage registrationStage;
    private Stage rootStage;
    private UserService userService;
    private String appName;
    private ResourceBundle resources;
    private Preferences loginPreferences;
    private RegistrationController controller;
    private User loginUser;
    private FXMLLoader registrationloader;
    private FXMLLoader mainProgramloader;
    private AnchorPane registrationView;
    private BorderPane rootViewPane;

    public AuthenticateController() {
        userService = new UserService();
        appName = "Клиентская база";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setClearedButtonsToFields();
        initRegistrationView();
        initLastLoginedInfo();
    }

    private void setClearedButtonsToFields() {
        ClearedButtonSetter.setButtonToTextField(loginTextField);
        ClearedButtonSetter.setButtonToPasswordField(passwordTextField);
    }

    private void initLastLoginedInfo() {
        loginPreferences = Preferences.userRoot().node("tempData");
        loginTextField.setText(loginPreferences.get("log", ""));
        passwordTextField.setText(loginPreferences.get("pas", ""));
    }

    public void onClickedRegistrationButton(MouseEvent mouseEvent) {
        showRegistrationWindow();
    }

    public void initRegistrationView() {
        try {
            registrationloader = new FXMLLoader(getClass().getResource(registrationViewLocation));
            registrationView = registrationloader.load();
            controller = registrationloader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRegistrationWindow() {
        if (registrationStage == null) {
            registrationStage = new Stage();
            registrationStage.initModality(Modality.WINDOW_MODAL);
            registrationStage.initOwner(authenticateStage);
            registrationStage.setScene(new Scene(registrationView));
            registrationStage.setResizable(false);
        }
        controller.setAuthenticateStage(authenticateStage);
        controller.setRegistrationStage(registrationStage);

        authenticateStage.hide();
        registrationStage.showAndWait();
        if (controller.isNewUserAdded()) {
            initLastLoginedInfo();
        }
        authenticateStage.show();
        loginTextField.requestFocus();
    }

    public void onLoginTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (passwordTextField.getText().equals("")) {
                passwordTextField.requestFocus();
            } else {
                doAuthentication();
            }
        }
    }

    public void onPasswordTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            doAuthentication();
        }
    }

    private void doAuthentication() {
        if (isLoginInfoValid()) {
            putLoginPreferences();
            UserSetter.setUser(loginUser);
            AlertCaster.castInfoAlert("Вы успешно залогинились!");
            closeAuthenticatesWindows();
            HibernateSessionFactory.setConnectionUrlLogin(loginUser.getLogin());

            showMainProgramView();
        }
    }

    private void putLoginPreferences() {
        loginPreferences.put("log", loginTextField.getText());
        loginPreferences.put("pas", passwordTextField.getText());
    }

    private void closeAuthenticatesWindows() {
        if (authenticateStage.isShowing()) {
            authenticateStage.close();
        }
        if (registrationStage != null) {
            registrationStage.close();
        }
    }

    private void showMainProgramView() {
        try {
            mainProgramloader = new FXMLLoader(getClass().getResource(rootViewLocation));
            mainProgramloader.setResources(resources);

            rootViewPane = mainProgramloader.load();
            rootStage = new Stage();
            rootStage.setScene(new Scene(rootViewPane));

            setOnCloseRequest();

            rootStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOnCloseRequest() {
        rootStage.setOnCloseRequest(event -> {
            Session session = SessionConnector.session;
            SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        });
    }

    private boolean isLoginInfoValid() {
        if (!isPasswordValid()) {
            AlertCaster.castInfoAlert("Логин или пароль неверный!");
            return false;
        } else {
            return true;
        }
    }

    private boolean isPasswordValid() {
        if (isLoginValid()) {
            loginUser = userService.getByKey(loginTextField.getText());
            if (loginUser.getPassword().equals(passwordTextField.getText())) {
                return true;
            }
        }
        return false;
    }

    private boolean isLoginValid() {
        if (userService.getByKey(loginTextField.getText()) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void onMouseClickedEnterButton(MouseEvent mouseEvent) {
        doAuthentication();
    }

    public void setAuthenticateStage(Stage authenticateStage) {
        this.authenticateStage = authenticateStage;
    }
}