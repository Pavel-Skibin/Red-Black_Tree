package org.nahap.gui.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Format;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class GraphRenderer {

    public static void renderTreeOnCanvas(String dot, Canvas canvas) {
        try {
            // Создаем изображение из DOT
            BufferedImage bufferedImage = Graphviz.fromString(dot)
                    .render(Format.PNG)
                    .toImage();

            // Конвертируем в формат, который может использовать JavaFX
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageData = baos.toByteArray();

            Image image = new Image(new ByteArrayInputStream(imageData));
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Очищаем холст
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Получаем размеры холста и изображения
            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();

            // Вычисляем масштаб для обоих измерений
            double scaleX = canvasWidth / imageWidth;
            double scaleY = canvasHeight / imageHeight;

            // Используем минимальный масштаб, чтобы изображение поместилось целиком
            double scale = Math.min(scaleX, scaleY);

            // Вычисляем размеры масштабированного изображения
            double scaledWidth = imageWidth * scale;
            double scaledHeight = imageHeight * scale;

            // Вычисляем позицию для центрирования
            double x = (canvasWidth - scaledWidth) / 2;
            double y = (canvasHeight - scaledHeight) / 2;

            // Отрисовываем изображение
            gc.drawImage(image, x, y, scaledWidth, scaledHeight);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}