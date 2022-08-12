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

    /** Main method. Entry point into the application.
     *
     * FUTURE ENHANCEMENT: For parts, they can be InHouse or Outsourced and each of those has an additional field either
     * MachineId or Company Name. Currently, for Outsourced parts, because company name is a String input it is not incorrect for it to be a
     * string of numbers "1234" for instance. It is however very unlikely that a company might be named only numbers,
     * and it could easily get stored as such in error when one changes the type of the part from InHouse to Outsourced.
     * In the future I would like to validate this with some regular expression or some criteria in the logic
     * of the AddPart and ModifyPart controllers. For instance, a company name could start with a letter or number,
     * must contain letters, can contain spaces or numbers as well, and can end with a letter or number. */
    public static void main(String[] args) {
        launch();
    }
}