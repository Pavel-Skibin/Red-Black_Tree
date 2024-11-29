package org.nahap.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui.fxml")));

        Scene scene = new Scene(viewport);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("R-B_TREE");
        stage.setScene(scene);

        stage.setMinWidth(1080);
        stage.setMinHeight(720);

        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
