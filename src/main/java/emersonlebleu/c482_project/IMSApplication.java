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
        FXMLLoader addPartLoader = new FXMLLoader(IMSApplication.class.getResource("add_part_view.fxml"));
        FXMLLoader modifyPartLoader = new FXMLLoader(IMSApplication.class.getResource("modify_part_view.fxml"));
        FXMLLoader addProductLoader = new FXMLLoader(IMSApplication.class.getResource("add_product_view.fxml"));
        FXMLLoader modifyProductLoader = new FXMLLoader(IMSApplication.class.getResource("modify_product_view.fxml"));

        stage.setTitle("Inventory Management System");

        Scene mainScene = new Scene(mainLoader.load(), 895.0 , 395.0);
        Scene addPartScene = new Scene(addPartLoader.load(), 895.0 , 395.0);
        Scene modifyPartScene = new Scene(modifyPartLoader.load(), 895.0 , 395.0);
        Scene addProductScene = new Scene(addProductLoader.load(), 895.0 , 395.0);
        Scene modifyProductScene = new Scene(modifyProductLoader.load(), 895.0 , 395.0);

        stage.setScene(mainScene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}