package org.nahap.gui.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.nahap.tree.binarytree.BTree;
import org.nahap.tree.rbtree.RedBlackTree;

public class GraphRenderer {

    public static final double NODE_DIAMETER = 90; // Диаметр окружности
    public static final double HORIZONTAL_INDENT = 20;
    public static final double VERTICAL_INDENT = 30;

    public static class NodeDrawResult {
       public double center;
        public double maxX;
        public double maxY;

        NodeDrawResult(double center, double maxX, double maxY) {
            this.center = center;
            this.maxX = maxX;
            this.maxY = maxY;
        }
    }


    private static NodeDrawResult paintNode(BTree.TreeNode<?> node, GraphicsContext gc, double x, double y) {
        if (node == null) {
            return null;
        }

        double dynamicHorizontalSpacing =   HORIZONTAL_INDENT;
        NodeDrawResult leftResult = paintNode(node.getLeft(), gc, x, y + NODE_DIAMETER + VERTICAL_INDENT);

        double rightX = (leftResult != null)
                ? leftResult.maxX + dynamicHorizontalSpacing
                : x + dynamicHorizontalSpacing;

        NodeDrawResult rightResult = paintNode(node.getRight(), gc, rightX, y + NODE_DIAMETER + VERTICAL_INDENT);
        double thisX;
        if (leftResult == null) {
            thisX = x;
        } else if (rightResult == null) {
            thisX = Math.max(x, leftResult.center + HORIZONTAL_INDENT);
        } else {
            thisX = (leftResult.center + rightResult.center) / 2 - NODE_DIAMETER / 2;
        }

        Color nodeColor;
        if (node instanceof RedBlackTree.RBTreeNode rbNode) {
            nodeColor = rbNode.color == RedBlackTree.RED ? Color.RED : Color.BLACK;
        } else {
            nodeColor = Color.BLACK; // По умолчанию черный, если узел не является частью красно-черного дерева
        }

        double centerX = thisX + NODE_DIAMETER / 2;
        double centerY = y + NODE_DIAMETER / 2;

        gc.setFill(nodeColor);
        gc.fillOval(thisX, y, NODE_DIAMETER, NODE_DIAMETER);
        gc.setStroke(Color.WHITE);
        gc.strokeOval(thisX, y, NODE_DIAMETER, NODE_DIAMETER);


        if (leftResult != null) {
            gc.setStroke(Color.GRAY);
            gc.strokeLine(centerX, y + NODE_DIAMETER, leftResult.center, y + NODE_DIAMETER + VERTICAL_INDENT);
        }
        if (rightResult != null) {
            gc.setStroke(Color.GRAY);
            gc.strokeLine(centerX, y + NODE_DIAMETER, rightResult.center, y + NODE_DIAMETER + VERTICAL_INDENT);
        }

        String text = node.getValue().toString();

        if (text.length() >= 7) {

            gc.setFont(Font.font("Arial", NODE_DIAMETER / 4)); // Меньший шрифт
            String line1 = text.substring(0, text.length() / 2);
            String line2 = text.substring(text.length() / 2);


            Text tempText1 = new Text(line1);
            tempText1.setFont(gc.getFont());
            double textWidth1 = tempText1.getBoundsInLocal().getWidth();

            Text tempText2 = new Text(line2);
            tempText2.setFont(gc.getFont());
            double textWidth2 = tempText2.getBoundsInLocal().getWidth();

            gc.setFill(Color.WHITE);
            gc.fillText(line1, centerX - textWidth1 / 2, centerY - 5); // Верхняя строка
            gc.fillText(line2, centerX - textWidth2 / 2, centerY + 15); // Нижняя строка
        } else {

            gc.setFont(Font.font("Arial", NODE_DIAMETER / 3));


            Text tempText = new Text(text);
            tempText.setFont(gc.getFont());
            double textWidth = tempText.getBoundsInLocal().getWidth();
            double textHeight = tempText.getBoundsInLocal().getHeight();

            double textX = centerX - textWidth / 2;
            double textY = centerY + textHeight / 4; // Смещение для центрирования


            gc.setFill(Color.WHITE);
            gc.fillText(text, textX, textY);
        }

        double maxX = Math.max((leftResult == null) ? 0 : leftResult.maxX, (rightResult == null) ? 0 : rightResult.maxX);
        double maxY = Math.max((leftResult == null) ? 0 : leftResult.maxY, (rightResult == null) ? 0 : rightResult.maxY);
        return new NodeDrawResult(centerX, Math.max(thisX + NODE_DIAMETER, maxX), Math.max(y + NODE_DIAMETER, maxY));
    }


    public static NodeDrawResult renderTreeOnCanvas(BTree.TreeNode<?> root, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        NodeDrawResult rootResult = null;
        if (root != null) {
            rootResult = paintNode(root, gc, HORIZONTAL_INDENT, 20);
        }

        return rootResult;
    }

    public static NodeDrawResult calculateTreeDimensions(BTree.TreeNode<?> root) {
        if (root == null) return new NodeDrawResult(0, 0, 0);

        NodeDrawResult leftResult = calculateTreeDimensions(root.getLeft());
        NodeDrawResult rightResult = calculateTreeDimensions(root.getRight());

        double maxX = Math.max(leftResult.maxX, rightResult.maxX) + NODE_DIAMETER + HORIZONTAL_INDENT;
        double maxY = Math.max(leftResult.maxY, rightResult.maxY) + NODE_DIAMETER + VERTICAL_INDENT;

        return new NodeDrawResult(
                (leftResult.center + rightResult.center) / 2,
                maxX,
                maxY
        );
    }

}