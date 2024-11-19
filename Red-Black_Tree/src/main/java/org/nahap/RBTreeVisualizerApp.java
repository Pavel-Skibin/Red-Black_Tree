package org.nahap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class RBTreeVisualizerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();


        RBTree<Integer> tree = new RBTree<>();
        tree.put(10);
        tree.put(5);
        tree.put(15);
        RBTreeExporter<Integer> exporter = new RBTreeExporter<>();
        try {
            exporter.exportToGraphviz(tree, "rbtree.png");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Отображение PNG в JavaFX
        Image image = new Image(new File("rbtree.png").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(true);

        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Red-Black Tree Visualizer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
