module projetotcc {
    requires javafx.controls;
    requires javafx.fxml;

    opens projetotcc to javafx.fxml;
    exports projetotcc;
    requires com.google.gson;
    requires java.net.http;
    
    opens entidades to com.google.gson;
    opens Dto to com.google.gson;
     
}
