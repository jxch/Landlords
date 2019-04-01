package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("./loginAndRegistered/login.fxml"));
        primaryStage.setTitle("Landlords - login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void nextStage(Node node, String fxml, String title) throws IOException {
        Stage stage = new Stage();
        Pane root = FXMLLoader.load(Main.class.getResource(fxml));
        stage.setScene(new Scene(root));
        node.getParent().getScene().getWindow().hide();
        stage.show();
        stage.setResizable(false);
        stage.setTitle(title);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
