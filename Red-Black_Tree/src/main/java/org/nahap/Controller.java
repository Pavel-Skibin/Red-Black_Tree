package org.nahap;

import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Format;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField textField;



    private final RBTree<Integer> tree = new RBTree<>();

    @FXML
    public void initialize() {
        AnchorPane parentPane = (AnchorPane) canvas.getParent();


        parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scrollWidth = scrollPane.getWidth();
            double newCanvasWidth = newVal.doubleValue() - scrollWidth - 30;
            canvas.setWidth(newCanvasWidth);
        });

        parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue() - 20);
        });

        for (int i = 3; i <= 9; i++) {
            int m = (int) (i * Math.pow(-1, i));
            tree.put(i);
            tree.put(m);
        }

        renderTree();
    }

    @FXML
    private void handleAdd() {
        String input = textField.getText();
        try {
            int value = Integer.parseInt(input);
            tree.put(value);
            renderTree();
        } catch (NumberFormatException e) {
            System.err.println("Введите корректное число.");
        }
    }

    @FXML
    private void handleDelete() {
        String input = textField.getText();
        try {
            int value = Integer.parseInt(input);
            tree.remove(value);
            renderTree();
        } catch (NumberFormatException e) {
            System.err.println("Введите корректное число.");
        }
    }

    private void renderTree() {
        String dot = RBTreeGraphvizConverter.convertToDot(tree);
        Platform.runLater(() -> {
            try {
                BufferedImage bufferedImage = Graphviz.fromString(dot)
                        .render(Format.PNG)
                        .toImage();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                byte[] imageData = baos.toByteArray();

                Image image = new Image(new ByteArrayInputStream(imageData));

                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                double canvasWidth = canvas.getWidth();
                double canvasHeight = canvas.getHeight();
                double imageRatio = image.getWidth() / image.getHeight();
                double canvasRatio = canvasWidth / canvasHeight;

                double drawWidth, drawHeight, x, y;
                if (imageRatio > canvasRatio) {
                    drawWidth = canvasWidth;
                    drawHeight = canvasWidth / imageRatio;
                    x = 0;
                    y = (canvasHeight - drawHeight) / 2;
                } else {
                    drawHeight = canvasHeight;
                    drawWidth = canvasHeight * imageRatio;
                    x = (canvasWidth - drawWidth) / 2;
                    y = 0;
                }

                gc.drawImage(image, x, y, drawWidth, drawHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
