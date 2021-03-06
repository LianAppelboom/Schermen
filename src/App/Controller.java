package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField loginTextField;
    @FXML
    private Text loginMessageLabel;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM cooldown.gebruiker where emailadres = '" + loginTextField.getText() + "' AND wachtwoord = '" + passwordTextField.getText() + "'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    dashBoardRedirect();
                } else {
                    validateLoginBeheerder();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void validateLoginBeheerder() {      //Beheerder Validation
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String verifyLoginB = "SELECT count(1) FROM cooldown.beheerder where emailadres = '" + loginTextField.getText() + "' AND wachtwoord = '" + passwordTextField.getText() + "'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResultB = statement.executeQuery(verifyLoginB);
            while (queryResultB.next()) {
                if (queryResultB.getInt(1) == 1) {
                    dashBoardRedirect();
                } else {
                    loginMessageLabel.setText("Invalid login! Please try again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    private void dashBoardRedirect() throws Exception {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String verifyLogin = "SELECT fles_id FROM cooldown.gebruiker where emailadres = '" + loginTextField.getText() + "' AND wachtwoord = '" + passwordTextField.getText() + "'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                Data.text = queryResult.getString("fles_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        BorderPane root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        borderPane.getChildren().setAll(root);
    }

    @FXML
    private void registratieRedirect(ActionEvent even) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("registratieForm.fxml"));
        borderPane.getChildren().setAll(root);
    }
}
