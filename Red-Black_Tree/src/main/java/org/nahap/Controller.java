package org.nahap;

import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Format;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.nahap.RBTreeGraphvizConverter;

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
    public void initialize() {
        AnchorPane parentPane = (AnchorPane) canvas.getParent();

        // Динамическая настройка размера Canvas относительно ScrollPane
        parentPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scrollWidth = scrollPane.getWidth();
            double newCanvasWidth = newVal.doubleValue() - scrollWidth - 30; // Учитываем отступы
            canvas.setWidth(newCanvasWidth);
        });

        parentPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue() - 20); // Учитываем отступы
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        RBTree<Integer> tree = new RBTree<>();
        for (int i = 3; i <= 500; i+=1) {
            tree.put(i);
        }

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

                // Масштабирование изображения по размеру Canvas с сохранением пропорций
                double canvasWidth = canvas.getWidth();
                double canvasHeight = canvas.getHeight();
                double imageRatio = image.getWidth() / image.getHeight();
                double canvasRatio = canvasWidth / canvasHeight;

                double drawWidth, drawHeight, x, y;
                if (imageRatio > canvasRatio) {
                    // Изображение шире, чем Canvas
                    drawWidth = canvasWidth;
                    drawHeight = canvasWidth / imageRatio;
                    x = 0;
                    y = (canvasHeight - drawHeight) / 2;
                } else {
                    // Изображение выше, чем Canvas
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