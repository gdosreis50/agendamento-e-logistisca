package com.mycompany.projetotcc;


import com.mycompany.projetotcc.entidades.Motorista;
import com.mycompany.projetotcc.service.MotoristaService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
            try {
                Date data = new Date();
                data.getTime();
                
                if(MotoristaService.novoMotorista("Cassio", "14789632599", "000000", data , "E", "10222222222", 2)){
                    System.out.println("Yes");
                }else{
                    System.out.println("No!");
                }
                
                //List<Motorista> lista = MotoristaService.listarMotorista();

                //for (Motorista m : lista) {
                //    System.out.println(
                //            "ID: " + m.getIdMotorista() +
                //            " | Nome: " + m.getNomeMotorista()+
                //            " | Funcionario FK: " + m.getFuncionario()
                //    );
                
                
                //Motorista motorista = new Motorista();
                
                //motorista = MotoristaService.getMotorista(2);
                
                //System.out.print(
                //        motorista.getNomeMotorista() + " | " +
                //        motorista.getCpf() + " | " +
                //        motorista.getIdMotorista() + " | "
                //);
            
            
            //launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}