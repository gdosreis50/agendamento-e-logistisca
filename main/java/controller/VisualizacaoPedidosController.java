/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entidades.Pedido;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projetotcc.App;
import service.PedidoService;

/**
 * FXML Controller class
 *
 * @author work & college
 */
public class VisualizacaoPedidosController implements Initializable {

    @FXML
    private Button btAtualizaPed;
    @FXML
    private ButtonBar barraBt;
    @FXML
    private Button btViewCheck;
    @FXML
    private Button btViewVeiculo;
    @FXML
    private Button btViewTransportadora;
    @FXML
    private Button btViewAgendamento;
    @FXML
    private Button btViewPedidos;
    @FXML
    private Button btViewMot;
    @FXML
    private TableView<Pedido> tabelaPed;
    @FXML
    private TableColumn<Pedido, Integer> colunaID;
    @FXML
    private TableColumn<Pedido, String> colunaNomeCliente;
    @FXML
    private TableColumn<Pedido, String> colunaNumPed;
    @FXML
    private TableColumn<Pedido, String> colunaCidadeCliente;
    @FXML
    private TableColumn<Pedido, Integer> colunaNumSacos;
    @FXML
    private TableColumn<Pedido, Integer> colunaNumPalete;
    @FXML
    private TableColumn<Pedido, Integer> colunaNumPaleteRomaneio;
    @FXML
    private TableColumn<Pedido, String> colunaNumRomaneio;
    @FXML
    private TableColumn<Pedido, String> colunaTransportadora;
    @FXML
    private TableColumn<Pedido, String> colunaStatusPed;

    
    
    private List<Pedido> listaPed;
    private ObservableList<Pedido> obsPedList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btViewPedidos.setDisable(true);
        
        PedidoService pedidos = new PedidoService();
        
        try {
            listaPed = pedidos.listarPedido();
            
            obsPedList = FXCollections.observableArrayList(listaPed);
            
            colunaID = new TableColumn("ID");
            colunaID.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getIdPedido()));//Pauzinho no ID

            colunaNumPed = new TableColumn("Número Pedido");
            colunaNumPed.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumPed()));
            
            colunaStatusPed = new TableColumn("Status");
            colunaStatusPed.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getStatusPed()));

            colunaNomeCliente = new TableColumn("Cliente");
            colunaNomeCliente.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNomeCliente()));

            colunaCidadeCliente = new TableColumn("Cidade");
            colunaCidadeCliente.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getCidadeCliente()));
            
            colunaNumSacos = new TableColumn("Qtd Sacos");
            colunaNumSacos.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumSacos()));
            
            colunaNumPalete = new TableColumn("Qtd Paletes");
            colunaNumPalete.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumPaletes()));
            
            colunaTransportadora = new TableColumn("Transportadora");
            colunaTransportadora.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getTransportadora()));
            
            colunaNumRomaneio = new TableColumn("Romaneio");
            colunaNumRomaneio.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumRomaneio()));
            
            colunaNumPaleteRomaneio = new TableColumn("Qtd Romaneio");
            colunaNumPaleteRomaneio.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNumPaleteRomaneio()));
            
            
            tabelaPed.getColumns().addAll(colunaID, colunaNumPed, colunaStatusPed, colunaNomeCliente, colunaCidadeCliente, colunaNumSacos, colunaNumPalete, colunaTransportadora, colunaNumRomaneio, colunaNumPaleteRomaneio);                
            tabelaPed.setItems(obsPedList);
            
            
        } catch (Exception ex) {
            Logger.getLogger(VisualizacaoPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void switchToCheck() throws IOException {
        App.setRoot("telaInicial");
    }

    @FXML
    private void switchToMotorista() throws IOException {
        App.setRoot("manutencaoMotorista");
    }
    
    @FXML
    private void switchToFuncionario() throws IOException{
        App.setRoot("manutencaoFuncionario");
    }
    
    @FXML
    private void switchToTransportadora() throws IOException{
        App.setRoot("manutencaoTransportadora");
    }
    
    @FXML
    private void switchToAgendamento() throws IOException{
        App.setRoot("agendamento");
    }
    
    @FXML
    private void abrirCheckComPedido(MouseEvent event){
        
        if(event.getClickCount() == 2){
            Pedido pedido = tabelaPed.getSelectionModel().getSelectedItem();
        
            if(pedido != null){
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/projetotcc/telaInicial.fxml"));
                    Parent root = loader.load();

                    TelaInicialController controller = loader.getController();
                    controller.initPedido(pedido);

                    tabelaPed.getScene().setRoot(root);


                }catch (IOException ex) {
                        Logger.getLogger(VisualizacaoPedidosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @FXML
    private void atualizarPed(){
        PedidoService novosPedidos = new PedidoService();
        List<Pedido> novaLista;
        
        try {
            novaLista = novosPedidos.listarPedido();
            obsPedList.clear();
            obsPedList.addAll(novaLista);
            
        } catch (Exception ex) {
            Logger.getLogger(VisualizacaoPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}