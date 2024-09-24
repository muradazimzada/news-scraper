module com.ncone.newparserinterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens model to com.fasterxml.jackson.databind;
    opens com.ncone.newparserinterface to javafx.fxml;
    exports com.ncone.newparserinterface;
}