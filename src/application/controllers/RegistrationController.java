package application.controllers;

import application.sql.entitys.authenticate.User;
import application.sql.services.authenticate.UserService;
import application.sql.TableCreator;
import application.util.AlertCaster;
import application.util.ClearedButtonSetter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class RegistrationController implements Initializable {
    public CustomTextField loginTextField;
    public CustomPasswordField passwordTextField;
    public Button cancelButton;
    public Button registrationButton;
    public CustomPasswordField passwordConfirmTextField;

    private Stage authenticateStage;
    private Stage registrationStage;
    private UserService userService;
    private boolean isNewUserAdded = false;
    private Preferences loginPreferences;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClearedButtonSetter.setButtonToTextField(loginTextField);
        ClearedButtonSetter.setButtonToPasswordField(passwordTextField);
        ClearedButtonSetter.setButtonToPasswordField(passwordConfirmTextField);

        userService = new UserService();
        loginPreferences = Preferences.userRoot().node("tempData");
    }

    public void onLoginTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            passwordTextField.requestFocus();
        }

    }

    public void onPasswordTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            passwordConfirmTextField.requestFocus();
        }
    }

    public void onPasswordConfirmTextFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            addNewUserToDB();
        }
    }

    public void onClickedRegistrationButton(MouseEvent mouseEvent) {
        addNewUserToDB();
    }

    private void addNewUserToDB() {
        if (infoIsCorrect()) {
            User user = new User(loginTextField.getText(), passwordTextField.getText());
            userService.add(user);
            userService.createDBSchemaForNewUser(user);
            createAllTablesForNewUser(user);
            isNewUserAdded = true;
            putLoginPreferences();
            System.out.println(user);
            registrationStage.close();
            AlertCaster.castInfoAlert("Пользователь успешно создан");
        }
    }

    private void createAllTablesForNewUser(User user) {
        TableCreator tableCreator = new TableCreator(user);
        tableCreator.createAllTables();
    }

    private void putLoginPreferences() {
        loginPreferences.put("log", loginTextField.getText());
        loginPreferences.put("pas", passwordTextField.getText());
    }

    private boolean infoIsCorrect() {
        if (loginTextField.getText().equals("") ||
                passwordTextField.getText().equals("") ||
                passwordConfirmTextField.getText().equals("")
        ) {
            AlertCaster.castInfoAlert("Заполните все поля!");
            return false;
        } else if (!passwordIsCorrect()) {
            AlertCaster.castInfoAlert("Пароли не совпадают!");
            return false;
        } else if (!loginIsCorret()) {
            AlertCaster.castInfoAlert("Такое имя пользователя уже сущевствует!");
            return false;
        } else {
            return true;
        }
    }

    private boolean passwordIsCorrect() {
        if (passwordTextField.getText().equals(passwordConfirmTextField.getText())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean loginIsCorret() {
        if (userService.getByKey(loginTextField.getText()) == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNewUserAdded() {
        return isNewUserAdded;
    }

    public void setNewUserAdded(boolean newUserAdded) {
        isNewUserAdded = newUserAdded;
    }

    public void onClickedCancelButton(ActionEvent mouseEvent) {
        registrationStage.hide();
    }

    public void setAuthenticateStage(Stage authenticateStage) {
        this.authenticateStage = authenticateStage;
    }

    public void setRegistrationStage(Stage registrationStage) {
        this.registrationStage = registrationStage;
    }
}
