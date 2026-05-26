package fr.flowly;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class FlowlyApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Load the splash screen at startup
        FXMLLoader splashLoader = new FXMLLoader(FlowlyApp.class.getResource("splash.fxml"));
        Scene scene = new Scene(splashLoader.load(), 400, 874);
        
        stage.setTitle("Flowly");
        stage.setScene(scene);
        stage.show();

        // 2. Create a 1-second pause transition
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        
        // 3. Switch to the onboarding screen once the countdown is finished
        pause.setOnFinished(event -> {
            try {
                // Load the onboarding FXML file
                FXMLLoader onboardingLoader = new FXMLLoader(FlowlyApp.class.getResource("onboarding.fxml"));
                Parent onboardingRoot = onboardingLoader.load();
                
                // Replace the current scene root with the onboarding view
                scene.setRoot(onboardingRoot);
                
            } catch (IOException e) {
                System.err.println("Error while loading the onboarding screen!");
                e.printStackTrace();
            }
        });

        // 4. Start the countdown
        pause.play();
    }
}