package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void registratieRedirect(ActionEvent even) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("registratieForm.fxml"));
        borderPane.getChildren().setAll(root);
    }

    @FXML
    private void dashBoardRedirect(ActionEvent even) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        borderPane.getChildren().setAll(root);
    }


}
