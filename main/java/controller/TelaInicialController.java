/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Dto.ChecklistDTO;
import entidades.Funcionario;
import entidades.Motorista;
import entidades.Pedido;
import entidades.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import projetotcc.App;
import service.ChecklistService;
import service.FuncionarioService;
import service.MotoristaService;
import service.PedidoService;
import service.VeiculoService;

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
    private ComboBox<Motorista> comboBoxMotorista;
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
    private ComboBox<Veiculo> comboBoxVeiculo;
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
    
    private List<Funcionario> listaFuncionario; 
    private FilteredList<Funcionario> listaFiltradaFunc;
    
    private List<Motorista> listaMotorista;
    private FilteredList<Motorista> listaFiltradaMotorista;
    
    private List<Veiculo> listaVeiculo;
    private FilteredList<Veiculo> listaFiltradaVeiculo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        FuncionarioService funcionarios = new FuncionarioService();
        
        try {
            
            listaFuncionario = funcionarios.listarFuncionarios();
            
            ObservableList<Funcionario> obsList = FXCollections.observableArrayList (listaFuncionario);
            
            listaFiltradaFunc = new FilteredList<>(obsList, p -> true);
            
            comboBoxFuncionario.setItems(listaFiltradaFunc);
            comboBoxFuncionario.setEditable(true);
            
            comboBoxFuncionario.setConverter(new StringConverter<Funcionario>() {
                @Override
                public String toString(Funcionario funcionario) {
                    return funcionario == null ? "" : funcionario.getNomeFunc();
                }

                @Override
                public Funcionario fromString(String string) {
                    return comboBoxFuncionario.getItems().stream()
                            .filter(f -> f.getNomeFunc().equals(string))
                            .findFirst().orElse(null);
                }
            });
            
            comboBoxFuncionario.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                
                if(event.getCode() == KeyCode.SPACE){
                    
                    if(comboBoxFuncionario.getEditor().isFocused()){
                        IndexRange selection = comboBoxFuncionario.getEditor().getSelection();
                        
                        if(selection.getLength() > 0){
                            comboBoxFuncionario.getEditor().replaceText(selection, " ");
                        }
                    }
                }
            });
            
            comboBoxFuncionario.getEditor().textProperty().addListener((obs,oldValue,newValue) -> {
                listaFiltradaFunc.setPredicate(funcionario -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                        
                    return funcionario.getNomeFunc().toLowerCase().contains(newValue.toLowerCase());
                        
                });
        
                if (comboBoxMotorista.getEditor().isFocused() && !newValue.isEmpty()){
                    comboBoxMotorista.show();
                }
                    
            });
        
            
            comboBoxMotorista.getEditor().setOnKeyReleased(event -> {
            if(event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN && event.getCode() != KeyCode.ENTER){
                comboBoxMotorista.getEditor().positionCaret(comboBoxMotorista.getEditor().getText().length());
            }
        });
        
            
            
        } 
        catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MotoristaService motoristas = new MotoristaService();
        
        // Ocorre exceção ao utilizar espaço (ASCCI 32). Mas, o filtro funciona para o primeiro nome.
        // Excessão deixou de ocorrer, mas ainda há problemas com o Space. Agora, ao pressioná-lo, seleciona-se o item mais próximo à consulta
        try {
            listaMotorista = motoristas.listarMotorista();
            
            ObservableList<Motorista> motObsList = FXCollections.observableArrayList(listaMotorista);
            
            listaFiltradaMotorista = new FilteredList<>(motObsList, p -> true);
            
            comboBoxMotorista.setItems(listaFiltradaMotorista);
            comboBoxMotorista.setEditable(true);
            comboBoxMotorista.setVisibleRowCount(5);
            
            comboBoxMotorista.setConverter(new StringConverter<Motorista>(){
                
                public String toString(Motorista motorista){
                    return motorista == null ? "" : motorista.getNomeMotorista();
                }

                @Override
                public Motorista fromString(String string) {
                    return comboBoxMotorista.getItems().stream()
                            .filter(m -> m.getNomeMotorista().equals(string))
                            .findFirst().orElse(null);
                }
            });
            
            comboBoxMotorista.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                
                if(event.getCode() == KeyCode.SPACE){
                    
                    if(comboBoxMotorista.getEditor().isFocused()){
                        IndexRange selection = comboBoxMotorista.getEditor().getSelection();
                        
                        if(selection.getLength() > 0){
                            comboBoxMotorista.getEditor().replaceText(selection, " ");
                        }
                    }
                }
            });
                        
            
            comboBoxMotorista.getEditor().textProperty().addListener((obs,oldValue, newValue)->{
                
                Platform.runLater(() -> {
                    listaFiltradaMotorista.setPredicate(motorista -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    
                    return motorista.getNomeMotorista().toLowerCase().contains(newValue.toLowerCase());
                    
                });
                    
                if (comboBoxMotorista.getEditor().isFocused() && !newValue.isEmpty()){
                    comboBoxMotorista.show();
                }
                    
            });
        });
            
        comboBoxMotorista.getEditor().setOnKeyReleased(event -> {
            if(event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN && event.getCode() != KeyCode.ENTER){
                comboBoxMotorista.getEditor().positionCaret(comboBoxMotorista.getEditor().getText().length());
            }
        });
            
        } 
        catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        VeiculoService veiculos = new VeiculoService();
        
        try {
            
            listaVeiculo = veiculos.listarVeiculo();
            
            ObservableList<Veiculo> veiObjList = FXCollections.observableArrayList(listaVeiculo);
            
            listaFiltradaVeiculo = new FilteredList<>(veiObjList, p -> true);
            comboBoxVeiculo.setItems(listaFiltradaVeiculo);
            comboBoxVeiculo.setEditable(true);
            
            comboBoxVeiculo.setConverter(new StringConverter<Veiculo>(){
                @Override
                public String toString(Veiculo veiculo) {
                    return veiculo == null ? "" : veiculo.getPlaca();
                }

                @Override
                public Veiculo fromString(String string) {
                    return comboBoxVeiculo.getItems().stream()
                            .filter(v -> v.getPlaca().equals(string))
                            .findFirst().orElse(null);
                }
            });
            
            comboBoxVeiculo.getEditor().textProperty().addListener((obs,oldValue,newValue) -> {
                listaFiltradaVeiculo.setPredicate(veiculo -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                        
                    return veiculo.getPlaca().toLowerCase().contains(newValue.toLowerCase());
                        
                });
        
                comboBoxVeiculo.show();
            });
            
            
            
        } 
        catch (Exception ex) {
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
            
            if (pedido.getNumPaletes() != 0){
            
                int tara = pedido.getNumPaletes();
                
                tara = tara * 1000;
            
                txtCliente.setText(pedido.getNomeCliente());
                txtLiberado.setText(pedido.getStatusPed());
                txtNumPaletes.setText(String.valueOf(pedido.getNumPaletes()));
                txtCalcTaraMin.setText(String.valueOf(tara));
                txtTransp.setText(pedido.getTransportadora());
                txtTipoProduto.setText("Carolina Soil");
            }else{
                txtFieldNumPedido.setText("Não encontrado");
                txtTipoProduto.setText("");
                txtCliente.setText("");
                txtLiberado.setText("");
                txtNumPaletes.setText("");
                txtCalcTaraMin.setText("");
                txtTransp.setText("");
                txtTipoProduto.setText("");
            }
            
            
            
           
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            txtFieldNumPedido.setText("Não encontrado");
            txtTipoProduto.setText("");
            txtCliente.setText("");
            txtLiberado.setText("");
            txtNumPaletes.setText("");
            txtCalcTaraMin.setText("");
            txtTransp.setText("");
            txtTipoProduto.setText("");
        }
    }
    
    @FXML
    private void gerarCheckEHistorico (){
        
        if(comboBoxMotorista.getValue() == null || comboBoxFuncionario.getValue() == null || comboBoxVeiculo.getValue() == null || txtFieldNumPedido.getText() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Coloque um Pedido Valido, Seu nome, Motorista e Veículo!");
                
            alert.show();
                
            return;
        }
        
        
        ChecklistDTO check = new ChecklistDTO();
        
        check.setDataEmissao(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        
        check.setNumPed(txtFieldNumPedido.getText());
        check.setIdmotoristas(comboBoxMotorista.getValue().getIdMotorista());
        check.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
        check.setIdveiculo(comboBoxVeiculo.getValue().getIdVeiculo());
        check.setIdtransportadora(1);
        
        ChecklistService checklistService = new ChecklistService();
        
        try {
            boolean salvo = checklistService.novoChecklist(check);
            
            if  (salvo) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Carregamento Salvo");
                
                alert.show();
                
                txtFieldNumPedido.setText("");
                txtTipoProduto.setText("");
                txtCliente.setText("");
                txtLiberado.setText("");
                txtNumPaletes.setText("");
                txtCalcTaraMin.setText("");
                txtTransp.setText("");
                txtTipoProduto.setText("");
                comboBoxMotorista.setValue(null);
                comboBoxFuncionario.setValue(null);
                comboBoxVeiculo.setValue(null);
                
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu errado!");
                
                alert.show();
                
                txtFieldNumPedido.setText("");
                txtTipoProduto.setText("");
                txtCliente.setText("");
                txtLiberado.setText("");
                txtNumPaletes.setText("");
                txtCalcTaraMin.setText("");
                txtTransp.setText("");
                txtTipoProduto.setText("");
            }
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu muito errado! \n Coloque um Pedido Valido, Seu nome, Motorista e Veículo!");
                
                alert.show();
        }
        
        
        
    }
    
    @FXML
    private void preencherMot (){
        
        if(comboBoxMotorista.getValue() != null){
            Motorista motorista = comboBoxMotorista.getValue();
            
            txtCpfMot.setText(motorista.getCpf());
            txtCnhMot.setText(motorista.getCnh());
            txtCatCnh.setText(motorista.getCategoriaCnh());
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
            txtVencChn.setText(format.format(motorista.getDataVencimentoCnh()));
        
            txtTel.setText(motorista.getTelefone());
        }else{
            txtCpfMot.setText("");
            txtCnhMot.setText("");
            txtCatCnh.setText("");
            txtVencChn.setText("");
            txtTel.setText("");
        }
        
        
    }
    
    
}
