package com.jordonmcgowan.weatherpackage;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

import java.util.Objects;

/**
 * The WeatherConditionsMain class is
 * responsible for for setting required
 * JavaFX fields and launching the JavaFx
 * GUI.
 *
 * @author  Jordon McGowan
 * @version 1.0
 */

public class WeatherConditionsMain extends Application {

    /**
     * Override start() method from Application
     * to define JavaFX GUI.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set required parameters for the JavaFX GUI.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                "WeatherConditionsGUILayout.fxml")));
        primaryStage.setTitle("Weather Checker");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main Method is responsible for calling
     * the launch() method from the Application
     * class. launch() starts the JavaFX application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
