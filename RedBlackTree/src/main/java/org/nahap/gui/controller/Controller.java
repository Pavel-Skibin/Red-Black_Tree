package org.nahap.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.nahap.tree.rbtree.RedBlackTree;
import org.nahap.gui.view.GraphRenderer;
import org.nahap.tree.rbtree.RBTreeGraphvizConverter;

import java.io.*;

import static org.nahap.gui.view.GraphRenderer.HORIZONTAL_INDENT;
import static org.nahap.gui.view.GraphRenderer.VERTICAL_INDENT;

public class Controller {
    @FXML private Canvas canvas;
    @FXML private Pane canvasContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField textField;

    private final RedBlackTree<Integer> tree = new RedBlackTree<>();

    @FXML
    public void initialize() {
        setupCanvasResizing();
        populateInitialTree();
        renderTree();
    }

    private void populateInitialTree() {
        for (int i = 3; i <= 30; i+=4) {
            int m = (int) (i * Math.pow(-1, i));
            tree.put(i);
            tree.put(m);
        }
    }

    private void setupCanvasResizing() {

        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());


        canvasContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                renderTree();
            }
        });

        canvasContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                renderTree();
            }
        });
    }

    private void renderTree() {
        if (canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            return;
        }

        String dot = RBTreeGraphvizConverter.convertToDot(tree);
        System.out.println(dot);
        Platform.runLater(() -> {
            try {
                GraphRenderer.NodeDrawResult result = GraphRenderer.renderTreeOnCanvas(tree.getRoot(), canvas);

                double treeWidth = result.maxX + HORIZONTAL_INDENT;
                double treeHeight = result.maxY + VERTICAL_INDENT;

                canvas.setWidth(treeWidth);
                canvas.setHeight(treeHeight);
            } catch (Exception e) {
                System.err.println("Ошибка рендеринга: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }


    @FXML
    private void handleAdd() {
        try {
            int value = Integer.parseInt(textField.getText());
            tree.put(value);
            renderTree();
            textField.clear();
        } catch (NumberFormatException e) {
            System.err.println("Введите корректное число");
        }
    }

    @FXML
    private void handleDelete() {
        try {
            int value = Integer.parseInt(textField.getText());
            tree.remove(value);
            renderTree();
            textField.clear();
        } catch (NumberFormatException e) {
            System.err.println("Введите корректное число");
        }
    }

    @FXML
    private void handleSave() {
        try {
            String dot = RBTreeGraphvizConverter.convertToDot(tree);

            String svgContent = convertDotToSvg(dot);

            saveSvgToFile(svgContent);

        } catch (IOException e) {
            System.err.println("Ошибка сохранения файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String convertDotToSvg(String dot) throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tsvg");
        processBuilder.redirectErrorStream(true);  // Соединяем стандартный вывод и ошибки
        Process process = processBuilder.start();

        try (OutputStream os = process.getOutputStream()) {
            os.write(dot.getBytes());
            os.flush();
        }


        StringBuilder svgContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                svgContent.append(line).append("\n");
            }
        }


        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return svgContent.toString();
    }

    private void saveSvgToFile(String svgContent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG files", "*.svg"));
        File svgFile = fileChooser.showSaveDialog(new Stage());
        if (svgFile != null) {
            try (FileWriter writer = new FileWriter(svgFile)) {
                writer.write(svgContent);
                System.out.println("SVG сохранен в: " + svgFile.getAbsolutePath());
            }
        }
    }
}