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

        String verifyLogin = "SELECT count(1) FROM cooldown.user where email = '" + loginTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    System.out.println(verifyLogin);
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
        BorderPane root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        borderPane.getChildren().setAll(root);
    }

    @FXML
    private void registratieRedirect(ActionEvent even) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("registratieForm.fxml"));
        borderPane.getChildren().setAll(root);
    }
}
