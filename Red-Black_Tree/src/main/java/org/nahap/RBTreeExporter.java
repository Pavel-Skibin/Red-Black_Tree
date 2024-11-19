package org.nahap;

import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Factory.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static javafx.scene.paint.Color.color;

public class RBTreeExporter<T extends Comparable<? super T>> {
    public void exportToGraphviz(RBTree<T> tree, String outputPath) throws IOException {
        MutableGraph graph = mutGraph("RBTree").setDirected(true);
        addNodes(tree.root, graph);

        // Рендеринг дерева в файл
        Graphviz.fromGraph(graph).width(800).render(guru.nidi.graphviz.engine.Format.PNG)
                .toFile(new File(outputPath));
    }

    private void addNodes(RBTree.RBTreeNode node, MutableGraph graph) {
        if (node == null) {
            return;
        }

        String nodeId = String.valueOf(node.value);
        String nodeColor = node.color ? "red" : "black";

        graph.add(mutNode(nodeId)
                .add(Style.FILLED)
                .add("fillcolor", nodeColor)
                .add("color", nodeColor)
        );

        if (node.left != null) {
            graph.add(mutNode(nodeId).addLink(mutNode(String.valueOf(node.left.value))));
            addNodes(node.left, graph);
        }

        if (node.right != null) {
            graph.add(mutNode(nodeId).addLink(mutNode(String.valueOf(node.right.value))));
            addNodes(node.right, graph);
        }
    }
}
