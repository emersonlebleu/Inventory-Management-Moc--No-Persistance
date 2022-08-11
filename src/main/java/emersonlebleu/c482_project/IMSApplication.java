package emersonlebleu.c482_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Class IMSApplication overarching class for our application. The class inherits from Application which is a starting point for JavaFX applications. */
public class IMSApplication extends Application {

    /** Start method. Called by default by launching the application, starts the views the user sees. */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(IMSApplication.class.getResource("main_view.fxml"));

        stage.setTitle("IMS: Main");

        Scene mainScene = new Scene(mainLoader.load(), 895.0 , 395.0);
        stage.setScene(mainScene);
        stage.show();
    }

    /** Main method. Entry point into the application. */
    public static void main(String[] args) {
        launch();
    }
}