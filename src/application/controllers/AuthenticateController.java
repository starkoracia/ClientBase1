package application.controllers;

import application.sql.entitys.authenticate.User;
import application.sql.services.authenticate.UserService;
import application.sql.connectors.SessionConnector;
import application.util.AlertCaster;
import application.util.ClearedButtonSetter;
import application.sql.HibernateSessionFactory;
import application.util.ControllerManager;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private RegistrationController registrationController;
    private User loginUser;
    private FXMLLoader registrationloader;
    private FXMLLoader rootViewloader;
    private AnchorPane registrationView;
    private BorderPane rootViewPane;

    private RootViewController rootViewController;

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
            registrationController = registrationloader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRegistrationWindow() {
        if (registrationStage == null) {
            registrationStage = new Stage();
            setStage(getThisWindow(), registrationStage, "Регистрация", registrationView);
        }
        setRegistrationController();

        registrationShowAndWait();
    }

    private void setStage(Window owner, Stage newWindowStage, String title, Pane view) {
        newWindowStage.initModality(Modality.WINDOW_MODAL);
        newWindowStage.initOwner(owner);
        newWindowStage.setTitle(title);
        newWindowStage.setResizable(false);
        Scene scene = new Scene(view);
        newWindowStage.setScene(scene);
    }

    private void setRegistrationController() {
        registrationController.setAuthenticateStage(authenticateStage);
        registrationController.setRegistrationStage(registrationStage);
    }

    private void registrationShowAndWait() {
        authenticateStage.hide();
        registrationStage.showAndWait();
        if (registrationController.isNewUserAdded()) {
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
//            AlertCaster.castInfoAlert("Вы успешно залогинились!");
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
            rootViewloader = new FXMLLoader(getClass().getResource(rootViewLocation));
            rootViewPane = rootViewloader.load();

            rootViewController = rootViewloader.getController();
            ControllerManager.setRootViewController(rootViewController);

            rootStage = new Stage();
            rootStage.setScene(new Scene(rootViewPane));

            rootViewController.setRootViewStage(rootStage);
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

    public Window getThisWindow() {
        return loginTextField.getScene().getWindow();
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