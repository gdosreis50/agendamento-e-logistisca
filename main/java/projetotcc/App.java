package projetotcc;


import entidades.Motorista;
import entidades.Veiculo;
import service.MotoristaService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import service.VeiculoService;
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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/projetotcc/" + fxml + ".fxml"));
        System.out.println(fxmlLoader);
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {
            try {
                
                //if(MotoristaService.desativarMotorista(5)){
                //    System.out.println("Yes");
                //}else{
                //    System.out.print("No!");
                //}
                
            //================================================================================//    
                //Date data = new Date();
                //data.getTime();
                
                //if(MotoristaService.atualizaMotorista(2, "Anderson Jose Felipe", "12345678911", "15975325", data, "E", "14997815749", 2)){
                //    System.out.println("Yes");
                //}else{
                //    System.out.print("No!");
                //}
                
                
            //=================================================================================//
                //Date data = new Date();
                //data.getTime();
                
                //if(MotoristaService.novoMotorista("Cassio", "14789632599", "000000", data , "E", "10222222222", 2)){
                //    System.out.println("Yes");
                //}else{
                //    System.out.println("No!");
                //}
                
            //===============================================================================//    
                List<Veiculo> lista = VeiculoService.listarVeiculo();

                for (Veiculo v : lista) {
                    System.out.println(
                            "ID: " + v.getIdVeiculo()+
                            " | Nome: " + v.getPlaca()+
                            " | Tipo: " + v.getTipo() +
                            " | Funcionario FK: " + v.getFuncionario() + 
                            " | Vagao " + v.getVagoes()
                    );
                }
                
            //==================================================================================//   
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