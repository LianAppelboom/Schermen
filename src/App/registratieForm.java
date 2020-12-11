package App;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class registratieForm implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label noMatchLabel;
    @FXML
    private TextField naamTextField;
    @FXML
    private TextField emailadresTextField;


    @FXML
    private void loginRedirect(ActionEvent even) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        borderPane.getChildren().setAll(root);
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void registerButtonOnAction(ActionEvent event) {
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
        noMatchLabel.setText("");
        registerUser();
    } else {
        noMatchLabel.setText("Password does not match!");
    }

    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String naam = naamTextField.getText();
        String emailadres = emailadresTextField.getText();
        String wachtwoord = setPasswordField.getText();

        String insertFields = "INSERT INTO gebruiker(naam,emailadres,wachtwoord) VALUES ('";
        String insertValues = naam + "','" + emailadres + "','" + wachtwoord + "')";
        String insertToRegister= insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            noMatchLabel.setText("Geregistreerd!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void registrationMessage() {

    }


}
