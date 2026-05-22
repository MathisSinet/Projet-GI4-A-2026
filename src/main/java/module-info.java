module fr.flowly {
    requires transitive javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens fr.flowly to javafx.fxml;
    exports fr.flowly;
}