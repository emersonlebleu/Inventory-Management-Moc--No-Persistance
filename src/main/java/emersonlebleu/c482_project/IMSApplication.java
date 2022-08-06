package emersonlebleu.c482_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IMSApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IMSApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 702.0 , 395.0);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}