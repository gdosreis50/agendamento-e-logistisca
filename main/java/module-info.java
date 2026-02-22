module com.mycompany.projetotcc {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.projetotcc to javafx.fxml;
    exports com.mycompany.projetotcc;
    requires com.google.gson;
    requires java.net.http;
    
    opens com.mycompany.projetotcc.entidades to com.google.gson;
    opens Dto to com.google.gson;
     
}
