/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entidades.Funcionario;
import entidades.Pedido;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import projetotcc.App;
import service.FuncionarioService;
import service.PedidoService;

/**
 * FXML Controller class
 *
 * @author work & college
 */
public class TelaInicialController implements Initializable {

    @FXML
    private ButtonBar barraBt;
    @FXML
    private Button btViewMotorista;
    @FXML
    private Button btViewVeiculo;
    @FXML
    private Button btViewTransportadora;
    @FXML
    private Button btViewAgendamento;
    @FXML
    private Button btViewPedidos;
    @FXML
    private TextField txtFieldNumPedido;
    @FXML
    private ComboBox<?> comboBoxMotorista;
    @FXML
    private Label txtTipoProduto;
    @FXML
    private Label txtLiberado;
    @FXML
    private Label txtNumPaletes;
    @FXML
    private Label txtCliente;
    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;
    @FXML
    private Label txtCalcTaraMin;
    @FXML
    private Label txtPlaca;
    @FXML
    private Label txtTipoVeic;
    @FXML
    private Label txtPrimVagao;
    @FXML
    private Label txtSegVagao;
    @FXML
    private Label txtTercVagao;
    @FXML
    private Label txtTransp;
    @FXML
    private Label txtCpfMot;
    @FXML
    private Label txtCnhMot;
    @FXML
    private Label txtCatCnh;
    @FXML
    private Label txtVencChn;
    @FXML
    private Label txtTel;

    /**
     * Initializes the controller class.
     */
    
    private List listaFuncionario; 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FuncionarioService funcionarios = new FuncionarioService();
        
        try {
            
            listaFuncionario = funcionarios.listarFuncionarios();
            
            ObservableList<Funcionario> obsList = (ObservableList<Funcionario>) FXCollections.observableArrayList (listaFuncionario);
            
            comboBoxFuncionario.setItems(obsList);
            
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    @FXML
    private void switchToMotoristas() throws IOException {
        App.setRoot("manutencaoMotorista");
    }
    
    @FXML
    private void getPedido(){
        String numPed = txtFieldNumPedido.getText();
        
        PedidoService request = new PedidoService();
        
        try {           
            Pedido pedido = new Pedido();
            pedido = request.getPedido(numPed);
            
            int tara = pedido.getNumPaletes();
            
            tara = tara * 1000;
            
            txtCliente.setText(pedido.getNomeCliente());
            txtLiberado.setText(pedido.getStatusPed());
            txtNumPaletes.setText(String.valueOf(pedido.getNumPaletes()));
            txtCalcTaraMin.setText(String.valueOf(tara));
            txtTransp.setText(pedido.getTransportadora());
            txtTipoProduto.setText("Carolina Soil");
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            txtFieldNumPedido.setText("Não encontrado");
            txtCliente.setText("");
            txtLiberado.setText("");
            txtNumPaletes.setText("");
            txtCalcTaraMin.setText("");
            txtTransp.setText("");
            txtTipoProduto.setText("");
        }
    }
}
