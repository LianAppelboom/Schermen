package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField Temperatuur;

    @FXML
    private TextField TemperatuurG;

    @FXML
    private void loginRedirect(ActionEvent even) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        borderPane.getChildren().setAll(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComPortSendReceive.main("COM3");
        setTemperatuur();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                refreshTemperatuur();
            }
        }, 0, 10000);

    }

    public void setTemperatuur() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT temperatuur FROM cooldown.fles where Fles_ID = '" + Data.text + "';");
            while (queryResult.next()) {
                String temperatuur = queryResult.getString("temperatuur");
                Temperatuur.setText(temperatuur + "°C");
                TemperatuurG.setText(temperatuur + "°C");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void refreshTemperatuur() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT temperatuur FROM cooldown.fles where Fles_ID = '" + Data.text + "';");
            while (queryResult.next()) {
                String temperatuur = queryResult.getString("temperatuur");
                Temperatuur.setText(temperatuur + "°C");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }


    public void plusButtonAction(ActionEvent event) {

    }

    public void minusButtonAction(ActionEvent event) {

    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);

    }


}
