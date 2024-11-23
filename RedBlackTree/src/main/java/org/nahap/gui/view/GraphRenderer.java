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
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();

            double scaleX = canvasWidth / imageWidth;
            double scaleY = canvasHeight / imageHeight;


            double scale = Math.min(scaleX, scaleY);

            double scaledWidth = imageWidth * scale;
            double scaledHeight = imageHeight * scale;

            double x = (canvasWidth - scaledWidth) / 2;
            double y = (canvasHeight - scaledHeight) / 2;

            gc.drawImage(image, x, y, scaledWidth, scaledHeight);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}