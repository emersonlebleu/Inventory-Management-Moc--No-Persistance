package emersonlebleu.c482_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IMSApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(IMSApplication.class.getResource("main_view.fxml"));

        stage.setTitle("IMS: Main");

        Scene mainScene = new Scene(mainLoader.load(), 895.0 , 395.0);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}