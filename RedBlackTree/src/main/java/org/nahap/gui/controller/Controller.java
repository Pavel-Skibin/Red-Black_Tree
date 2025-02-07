package org.nahap.gui.controller;


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


public class Controller {
    @FXML private Canvas canvas;
    @FXML private Pane canvasContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField textField;

    private final RedBlackTree<Integer> tree = new RedBlackTree<>();




    // Добавляем эти поля в класс
    private double lastX;
    private double lastY;

    @FXML
    public void initialize() {
        setupScrollPane();
        setupDragHandlers();
        populateInitialTree();
        renderTree();
    }

    private void setupScrollPane() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPannable(true);

        canvas.setWidth(4000);
        canvas.setHeight(1000);

        canvasContainer.setPrefWidth(4000);
        canvasContainer.setPrefHeight(1000);
    }

    private void setupDragHandlers() {
        canvas.setOnMousePressed(e -> {
            lastX = e.getX();
            lastY = e.getY();
            canvas.setCursor(javafx.scene.Cursor.CLOSED_HAND);
        });

        canvas.setOnMouseDragged(e -> {
            double deltaX = e.getX() - lastX;
            double deltaY = e.getY() - lastY;

            scrollPane.setHvalue(scrollPane.getHvalue() - deltaX / (canvasContainer.getWidth() - scrollPane.getViewportBounds().getWidth()));
            scrollPane.setVvalue(scrollPane.getVvalue() - deltaY / (canvasContainer.getHeight() - scrollPane.getViewportBounds().getHeight()));

            lastX = e.getX();
            lastY = e.getY();
        });

        canvas.setOnMouseReleased(e -> canvas.setCursor(javafx.scene.Cursor.DEFAULT));
    }

    private void renderTree() {
        if (canvas.getWidth() <= 0 || canvas.getHeight() <= 0) return;

        GraphRenderer.NodeDrawResult result = GraphRenderer.renderTreeOnCanvas(tree.getRoot(), canvas);

        double requiredWidth = result.maxX + GraphRenderer.HORIZONTAL_INDENT * 2;
        double requiredHeight = result.maxY + GraphRenderer.VERTICAL_INDENT * 2;

        if (requiredWidth > canvas.getWidth()) {
            canvas.setWidth(requiredWidth);
            canvasContainer.setPrefWidth(requiredWidth);
        }

        if (requiredHeight > canvas.getHeight()) {
            canvas.setHeight(requiredHeight);
            canvasContainer.setPrefHeight(requiredHeight);
        }
    }



    private void populateInitialTree() {
        for (int i = 3; i <= 30; i+=1) {
            int m = (int) (i * Math.pow(-1, i));
            tree.put(i);
            tree.put(m);
        }
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