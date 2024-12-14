module org.nahap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires guru.nidi.graphviz;
    requires jdk.compiler;

    opens org.nahap.gui.controller to javafx.fxml;
    opens org.nahap.gui.view to javafx.fxml;
    exports org.nahap.gui;
}
