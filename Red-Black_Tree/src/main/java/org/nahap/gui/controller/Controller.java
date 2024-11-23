package org.nahap.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import org.nahap.rbtree.RBTree;
import org.nahap.gui.view.GraphRenderer;
import org.nahap.rbtree.RBTreeGraphvizConverter;

public class Controller {
    @FXML private Canvas canvas;
    @FXML private Pane canvasContainer;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField textField;

    private final RBTree<Integer> tree = new RBTree<>();

    @FXML
    public void initialize() {
        setupCanvasResizing();
        populateInitialTree();
        renderTree();
    }

    private void populateInitialTree() {
        for (int i = 3; i <= 9; i++) {
            int m = (int) (i * Math.pow(-1, i));
            tree.put(i);
            tree.put(m);
        }
    }

    private void setupCanvasResizing() {
        // Привязываем размер холста к размеру контейнера
        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());

        // Добавляем слушатели изменения размера
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
        Platform.runLater(() -> {
            try {
                GraphRenderer.renderTreeOnCanvas(dot, canvas);
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
}