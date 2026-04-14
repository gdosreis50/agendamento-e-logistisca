module projetotcc {
    requires javafx.controls;
    requires javafx.fxml;

    opens projetotcc to javafx.fxml;
    exports projetotcc;
    requires com.google.gson;
    requires java.net.http;
    requires java.base;
    requires javafx.swing;
    requires org.apache.pdfbox;
    
    opens entidades to com.google.gson;
    opens Dto to com.google.gson;

    
    
    opens controller to javafx.fxml;
    requires javafx.graphicsEmpty;
    
    requires java.logging;
     
}
