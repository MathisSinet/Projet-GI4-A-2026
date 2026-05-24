package fr.flowly;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onViewButtonClick() {
        welcomeText.setText("Your bubble in the chaos.");
    }
}
