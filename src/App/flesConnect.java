package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class flesConnect implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label noMatchLabel;
    @FXML
    private TextField pairField;


    @FXML
    private void loginRedirect() throws Exception {
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

    public void pairButtonOnAction(ActionEvent event) {
        if (pairField.getText().length() == 4) {
            noMatchLabel.setText("");
            pairCooldown();
        } else {
            noMatchLabel.setText("Pair Code must contain 4 characters!");
        }

    }

    public void pairCooldown() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String pairCode = pairField.getText();

        String insertFields = "UPDATE `cooldown`.`gebruiker` SET `Fles_ID` = '";
        String insertValues = pairCode + "' WHERE (`Gebruiker_ID` = ' " + Data.text + "');";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            noMatchLabel.setText("Paired!");
            loginRedirect();
        } catch (Exception e) {
            noMatchLabel.setText("pair code not valid! check for mistakes");
            e.printStackTrace();
            e.getCause();
        }
    }


}
