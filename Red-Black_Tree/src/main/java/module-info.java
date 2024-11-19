module org.nahap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires guru.nidi.graphviz;

    opens org.nahap to javafx.fxml;
    exports org.nahap;
}
