package org.nahap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Загружаем FXML файл
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui.fxml")));

        // Создаем сцену
        Scene scene = new Scene(viewport);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        // Настройки окна
        stage.setTitle("Simple3DViewer");
        stage.setScene(scene);

        // Установка минимальных размеров окна
        stage.setMinWidth(1080);
        stage.setMinHeight(720);

        // Установка размеров окна на весь экран
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}