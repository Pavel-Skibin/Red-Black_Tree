package org.nahap;

import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Format;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.nahap.RBTreeGraphvizConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Controller {

    @FXML
    private Canvas canvas;  // Ссылка на Canvas из FXML

    @FXML
    public void initialize() {
        // Получаем GraphicsContext для рисования
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Создание дерева
        RBTree<Integer> tree = new RBTree<>();
        for (int i = 1; i <= 10; i++) {
            tree.put(i);
        }

        // Генерация DOT-строки для дерева
        String dot = RBTreeGraphvizConverter.convertToDot(tree); // Предполагаем, что этот метод существует
        System.out.println(dot);

        try {
            // Генерация BufferedImage
            BufferedImage bufferedImage = Graphviz.fromString(dot)
                    .render(Format.PNG)
                    .toImage();

            // Конвертация BufferedImage в byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageData = baos.toByteArray();

            // Создание объекта JavaFX Image из массива байтов
            Image image = new Image(new ByteArrayInputStream(imageData));

            // Рисуем изображение на Canvas
            gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}