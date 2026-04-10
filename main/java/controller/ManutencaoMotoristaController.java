/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Dto.MotoristaDTO;
import entidades.Funcionario;
import entidades.Motorista;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import projetotcc.App;
import service.FuncionarioService;
import service.MotoristaService;
/**
 * FXML Controller class
 *
 * @author work & college
 */
public class ManutencaoMotoristaController implements Initializable {


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
    private Button btMot;
    @FXML
    private ComboBox<Motorista> comboBoxMotorista;
    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCpf;
    @FXML
    private TextField textFieldTelefone;
    @FXML
    private TextField textFieldCnh;
    @FXML
    private TextField textFieldCatCnh;
    @FXML
    private DatePicker dateSelector;
    @FXML
    private TableView<Motorista> tabelaMot;
    @FXML
    private TableColumn<Motorista, Integer> colunaID;
    @FXML
    private TableColumn<Motorista, String> colunaNome;
    @FXML
    private TableColumn<Motorista, String> colunaTelefone;
    @FXML
    private TableColumn<Motorista, String> colunaCat;
   
    private List<Funcionario> listaFuncionario; 
    private FilteredList<Funcionario> listaFiltradaFunc;
    
    private List<Motorista> listaMotorista;
    private FilteredList<Motorista> listaFiltradaMotorista;
    
    private boolean novo;
    private int idMot;
    
    private ObservableList<Motorista> motObsList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btMot.setDisable(true);
        novo = true;
        idMot = -1;
        
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
        
                if (comboBoxFuncionario.getEditor().isFocused() && !newValue.isEmpty()){
                    comboBoxFuncionario.show();
                }
                    
            });
        
            
            comboBoxFuncionario.getEditor().setOnKeyReleased(event -> {
            if(event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN && event.getCode() != KeyCode.ENTER){
                comboBoxFuncionario.getEditor().positionCaret(comboBoxFuncionario.getEditor().getText().length());
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
            
            motObsList = FXCollections.observableArrayList(listaMotorista);
            
            //Preenche tabela a direita para visualização
            colunaID = new TableColumn("ID");
            colunaID.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getIdMotorista()));
            
            colunaNome = new TableColumn("Nome");
            colunaNome.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNomeMotorista()));
            
            colunaTelefone = new TableColumn("Telefone");
            colunaTelefone.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getTelefone()));
            
            colunaCat = new TableColumn("Cat");
            colunaCat.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getCategoriaCnh()));
            
            tabelaMot.getColumns().addAll(colunaID, colunaNome, colunaTelefone, colunaCat);
            
            tabelaMot.setItems(motObsList);
            
            
            
            
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
    }    
    
    @FXML
    private void limparCampos() {
        textFieldNome.setText("");
        textFieldCpf.setText("");
        textFieldCnh.setText("");
        textFieldCatCnh.setText("");
        textFieldTelefone.setText("");
        dateSelector.setValue(null);
        comboBoxMotorista.setValue(null);
        comboBoxFuncionario.setValue(null);
        
        novo = true;
        idMot = -1;
    }

    @FXML
    private void salvarMot() {
        
        if(comboBoxFuncionario.getValue() == null || textFieldNome.getText() == null || textFieldCpf.getText() == null || textFieldCnh.getText() == null || textFieldCatCnh.getText() == null){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Coloque Seu nome e preencha todos os campos! \n Caso seu nome não esteja disponível, contate o TI.");
                
            alert.show();
            return;
        }    
            
            
            
            if(novo){
                MotoristaDTO motoristaDto = new MotoristaDTO();
            
                motoristaDto.setNomeMotorista(textFieldNome.getText());
                motoristaDto.setCpf(textFieldCpf.getText());
                motoristaDto.setCnh(textFieldCnh.getText());
                motoristaDto.setDataVencimentoCnh(dateSelector.getValue());
                motoristaDto.setCategoriaCnh(textFieldCatCnh.getText());
                motoristaDto.setTelefone(textFieldTelefone.getText());
                motoristaDto.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            
                MotoristaService motoristaService = new MotoristaService();
            
                try {
                
                    boolean salvo = motoristaService.novoMotorista(motoristaDto);
            
                    if  (salvo) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sistema");
                        alert.setHeaderText(null);
                        alert.setContentText("Motorista Salvo");
                
                        alert.show();
                        atualizarLista();
                        limparCampos();
                    
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sistema");
                        alert.setHeaderText(null);
                        alert.setContentText("Algo deu errado!");
                
                        alert.show();
                    
                        limparCampos();
                    }
                
                
                } catch (Exception ex) {
                    Logger.getLogger(ManutencaoMotoristaController.class.getName()).log(Level.SEVERE, null, ex);
                
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sistema");
                    alert.setHeaderText(null);
                    alert.setContentText("Algo deu muito errado! \n Coloque um Pedido Valido, Seu nome, Motorista e Veículo!");
                    
                    alert.show();
                
                    limparCampos();
                }
            }
            else{
                MotoristaDTO motoristaDto = new MotoristaDTO();
            
                motoristaDto.setNomeMotorista(textFieldNome.getText());
                motoristaDto.setCpf(textFieldCpf.getText());
                motoristaDto.setCnh(textFieldCnh.getText());
                motoristaDto.setDataVencimentoCnh(dateSelector.getValue());
                motoristaDto.setCategoriaCnh(textFieldCatCnh.getText());
                motoristaDto.setTelefone(textFieldTelefone.getText());
                motoristaDto.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            
                MotoristaService motoristaService = new MotoristaService();
            
            
                try {
                    boolean salvo = motoristaService.atualizaMotorista(idMot, motoristaDto);
                
                    if  (salvo) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sistema");
                        alert.setHeaderText(null);
                        alert.setContentText("Motorista Salvo");
                   
                        alert.show();
                        atualizarLista();
                        limparCampos();
                    
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sistema");
                        alert.setHeaderText(null);
                        alert.setContentText("Algo deu errado!");
                
                        alert.show();
                    
                        limparCampos();
                    }
                
                } catch (Exception ex) {
                    Logger.getLogger(ManutencaoMotoristaController.class.getName()).log(Level.SEVERE, null, ex);
                
                    Logger.getLogger(ManutencaoMotoristaController.class.getName()).log(Level.SEVERE, null, ex);
                
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sistema");
                    alert.setHeaderText(null);
                    alert.setContentText("Algo deu muito errado! \n Coloque um Pedido Valido, Seu nome, Motorista e Veículo!");
                
                    alert.show();
                
                    limparCampos();
                }
            }
    }

    @FXML
    private void preencherMot() {
        if(comboBoxMotorista.getValue() != null){
            Motorista motorista = comboBoxMotorista.getValue();
            
            idMot = motorista.getIdMotorista();
            
            textFieldNome.setText(motorista.getNomeMotorista());
            textFieldCpf.setText(motorista.getCpf());
            textFieldCnh.setText(motorista.getCnh());
            textFieldCatCnh.setText(motorista.getCategoriaCnh());        
            dateSelector.setValue(motorista.getDataVencimentoCnh());
            textFieldTelefone.setText(motorista.getTelefone());
            
            novo = false;
        }else{
            textFieldNome.setText("");
            textFieldCpf.setText("");
            textFieldCnh.setText("");
            textFieldCatCnh.setText("");
            dateSelector.setValue(null);
            textFieldTelefone.setText("");
        }
    }

    @FXML
    private void switchToCheck() throws IOException{
        App.setRoot("telaInicial");
    }
    
    @FXML
    private void switchToPedidos() throws IOException{
        App.setRoot("visualizacaoPedidos");
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
    private void carregaDadosDaTabela(MouseEvent event){
        if(event.getClickCount() == 2){
            Motorista motorista = tabelaMot.getSelectionModel().getSelectedItem();

            idMot = motorista.getIdMotorista();

            textFieldNome.setText(motorista.getNomeMotorista());
            textFieldCpf.setText(motorista.getCpf());
            textFieldCnh.setText(motorista.getCnh());
            textFieldCatCnh.setText(motorista.getCategoriaCnh());        
            dateSelector.setValue(motorista.getDataVencimentoCnh());
            textFieldTelefone.setText(motorista.getTelefone());

            novo = false;
        }
    }

    @FXML
    private void desativarMot(){
        MotoristaService mServ = new MotoristaService();
        
        try {
            if (idMot == -1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um Motorista");
                
                alert.show();
                return;
            }
            
            
            boolean desativado = mServ.desativarMotorista(idMot);
            
            if(desativado){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Motorista desativado!");

                alert.show();
                atualizarLista();
                limparCampos();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu errado!");
                
                alert.show();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ManutencaoTransportadoraController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Algo deu muito errado! Verifique os campos e se persistir, contate o TI!");

            alert.show();
        }
    }
    
    private void atualizarLista(){
        MotoristaService mot = new MotoristaService();
        List<Motorista> novaLista;
        try {
            novaLista = mot.listarMotorista();

            motObsList.clear();
            motObsList.addAll(novaLista);
   
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
