package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
