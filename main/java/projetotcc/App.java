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
        scene = new Scene(loadFXML("telaInicial"), 1280, 720);
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
                
                //if(VeiculoService.desativarVeiculo(1)){
                //    System.out.println("Yes");
                //}else{
                //    System.out.print("No!");
                //}
                
            //================================================================================//    
                //Date data = new Date();
                //data.getTime();
                
                //if(VeiculoService.atualizaVeiculo(1, "BVS8326", "Opalão", 2)){
                //   System.out.println("Yes");
                //}else{
                //    System.out.print("No!");
                //}
                
                
            //=================================================================================//
                //Date data = new Date();
                //data.getTime();
                
                //if(VeiculoService.novoVeiculo("FKC2323", "Truck", 2)){
                //    System.out.println("Yes");
                //}else{
                //    System.out.println("No!");
                //}
                
            //===============================================================================//    
                
                //List<Veiculo> lista = VeiculoService.listarVeiculo();

                //for (Veiculo v : lista) {
                //    System.out.println(
                //            "ID: " + v.getIdVeiculo()+
                //            " | Nome: " + v.getPlaca()+
                //            " | Tipo: " + v.getTipo() +
                //            " | Funcionario FK: " + v.getFuncionario() + 
                //            " | Vagao " + v.getVagoes()
                //    );
                //}
                
            //==================================================================================//   
                //Veiculo veiculo = new Veiculo();
                
                //veiculo = VeiculoService.getVeiculo(1);
                
                //System.out.print(
                //        veiculo.getPlaca()+ " | " +
                //        veiculo.getTipo()+ " | " +
                //        veiculo.getVagoes()+ " | "
                //);
            
            
            launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}