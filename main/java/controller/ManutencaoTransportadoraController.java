/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Dto.TransportadoraDTO;
import entidades.Funcionario;
import entidades.Transportadora;
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
import javafx.scene.control.IndexRange;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import projetotcc.App;
import service.FuncionarioService;
import service.TransportadoraService;

/**
 * FXML Controller class
 *
 * @author work & college
 */
public class ManutencaoTransportadoraController implements Initializable {

    @FXML
    private ButtonBar barraBt;
    @FXML
    private Button btViewCheck;
    @FXML
    private Button btViewTransportadora;
    @FXML
    private ComboBox<Transportadora> comboBoxTransportadora;
    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCnpj;
    @FXML
    private TextField textFieldAntt;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TableView<Transportadora> tabelaTransp;
    @FXML
    private TableColumn<Transportadora, Integer> colunaID;
    @FXML
    private TableColumn<Transportadora, String> colunaNomeTransp;
    @FXML
    private TableColumn<Transportadora, String> colunaEmail;
    
    private List<Funcionario> listaFuncionario;
    private FilteredList<Funcionario> listaFiltradaFunc;
    
    private List<Transportadora> listaTransp;
    private FilteredList<Transportadora> listafiltradaTransp;

    private boolean novo;
    private int idTransp;
    
    private ObservableList<Transportadora> transpObsList;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        novo = true;
        idTransp = -1;
        btViewTransportadora.setDisable(true);
        
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
        
        
        TransportadoraService transportadoras = new TransportadoraService();
        try {
            listaTransp = transportadoras.listarTransportadora();
            
            transpObsList = FXCollections.observableArrayList(listaTransp);
            
            
          //------- Tabela -----//
          
            colunaID = new TableColumn("ID");
            colunaID.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getIdTransportadora()));

            colunaNomeTransp = new TableColumn("Transportadora");
            colunaNomeTransp.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNomeTransportadora()));

            colunaEmail = new TableColumn("Contato");
            colunaEmail.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getEmail()));

            tabelaTransp.getColumns().addAll(colunaID, colunaNomeTransp, colunaEmail);
            
            tabelaTransp.setItems(transpObsList);
            
            
            
          //---- ComboBox -------//  
            
            listafiltradaTransp = new FilteredList<>(transpObsList, p -> true);
            
            comboBoxTransportadora.setItems(listafiltradaTransp);
            comboBoxTransportadora.setEditable(true);
            comboBoxTransportadora.setVisibleRowCount(5);
            
            comboBoxTransportadora.setConverter(new StringConverter<Transportadora>(){
                
                public String toString(Transportadora t){
                    return t == null ? "" : t.getNomeTransportadora();
                }

                @Override
                public Transportadora fromString(String string) {
                    return comboBoxTransportadora.getItems().stream()
                            .filter(m -> m.getNomeTransportadora().equals(string))
                            .findFirst().orElse(null);
                }
            });
            
            comboBoxTransportadora.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                
                if(event.getCode() == KeyCode.SPACE){
                    
                    if(comboBoxTransportadora.getEditor().isFocused()){
                        IndexRange selection = comboBoxTransportadora.getEditor().getSelection();
                        
                        if(selection.getLength() > 0){
                            comboBoxTransportadora.getEditor().replaceText(selection, " ");
                        }
                    }
                }
            });
                        
            
            comboBoxTransportadora.getEditor().textProperty().addListener((obs,oldValue, newValue)->{
                
                Platform.runLater(() -> {
                    listafiltradaTransp.setPredicate(transportadora -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    
                    return transportadora.getNomeTransportadora().toLowerCase().contains(newValue.toLowerCase());
                    
                });
                    
                if (comboBoxTransportadora.getEditor().isFocused() && !newValue.isEmpty()){
                    comboBoxTransportadora.show();
                }
                    
            });
        });
            
        comboBoxTransportadora.getEditor().setOnKeyReleased(event -> {
            if(event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN && event.getCode() != KeyCode.ENTER){
                comboBoxTransportadora.getEditor().positionCaret(comboBoxTransportadora.getEditor().getText().length());
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
        textFieldCnpj.setText("");
        textFieldAntt.setText("");
        textFieldEmail.setText("");
        comboBoxFuncionario.setValue(null);
        comboBoxTransportadora.setValue(null);
        
        novo = true;
        idTransp = -1;
    }

    @FXML
    private void salvarTransp() {
        if(textFieldNome.getText() == null || textFieldCnpj.getText() == null || textFieldAntt.getText() == null || textFieldEmail.getText() == null || comboBoxFuncionario.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
                
            alert.show();
            return;
        }
        
        if(novo){
            TransportadoraDTO transportadora = new TransportadoraDTO();
            
            transportadora.setNomeTransportadora(textFieldNome.getText());
            transportadora.setCnpj(textFieldCnpj.getText());
            transportadora.setAntt(textFieldAntt.getText());
            transportadora.setEmail(textFieldEmail.getText());
            transportadora.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            
            TransportadoraService tService = new TransportadoraService();
            
            try {
                boolean salvo = tService.novaTransportadora(transportadora);
                
                if(salvo){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sistema");
                    alert.setHeaderText(null);
                    alert.setContentText("Transportadora salva!");

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
                Logger.getLogger(ManutencaoTransportadoraController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu muito errado! Verifique os campos e se persistir, contate o TI!");

                alert.show();
                limparCampos();
            }
            
        }else{
            TransportadoraDTO transportadora = new TransportadoraDTO();
            
            transportadora.setNomeTransportadora(textFieldNome.getText());
            transportadora.setCnpj(textFieldCnpj.getText());
            transportadora.setAntt(textFieldAntt.getText());
            transportadora.setEmail(textFieldEmail.getText());
            transportadora.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            
            TransportadoraService tService = new TransportadoraService();
            
            try {
                boolean salvo = tService.atualizaTransportadora(idTransp, transportadora);
                
                if(salvo){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sistema");
                    alert.setHeaderText(null);
                    alert.setContentText("Transportadora salva!");

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
                Logger.getLogger(ManutencaoTransportadoraController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu muito errado! Verifique os campos e se persistir, contate o TI!");

                alert.show();
                limparCampos();
            }
        }
        
        
        
    }

    @FXML
    private void switchToCheck(ActionEvent event) throws IOException {
        App.setRoot("telaInicial");
    }
    
    @FXML
    private void switchToVeiculo(ActionEvent event) throws IOException {
        App.setRoot("manutencaoVeiculo");
    }

    @FXML
    private void switchToPedidos(ActionEvent event) throws IOException {
        App.setRoot("visualizacaoPedidos");
    }

    @FXML
    private void switchToMotoristas(ActionEvent event) throws IOException {
        App.setRoot("manutencaoMotorista");
    }

    @FXML
    private void switchToFuncionario(ActionEvent event) throws IOException {
        App.setRoot("manutencaoFuncionario");
    }
    
    @FXML
    private void switchToAgendamento() throws IOException{
        App.setRoot("agendamento");
    }

    @FXML
    private void preencherTransp(ActionEvent event) {
        if(comboBoxTransportadora.getValue() != null){
            novo = false;
            Transportadora t = comboBoxTransportadora.getValue();
            
            idTransp = t.getIdTransportadora();
            textFieldNome.setText(t.getNomeTransportadora());
            textFieldCnpj.setText(t.getCnpj());
            textFieldAntt.setText(t.getAntt());
            textFieldEmail.setText(t.getEmail());
        }else{
            textFieldNome.setText("");
            textFieldCnpj.setText("");
            textFieldAntt.setText("");
            textFieldEmail.setText("");
        }
    }

    @FXML
    private void carregaDadosDaTabela(MouseEvent event) {
        if(event.getClickCount() == 2){
            novo = false;
            Transportadora t = tabelaTransp.getSelectionModel().getSelectedItem();
            
            idTransp = t.getIdTransportadora();
            textFieldNome.setText(t.getNomeTransportadora());
            textFieldCnpj.setText(t.getCnpj());
            textFieldAntt.setText(t.getAntt());
            textFieldEmail.setText(t.getEmail());
        }else{
            textFieldNome.setText("");
            textFieldCnpj.setText("");
            textFieldAntt.setText("");
            textFieldEmail.setText("");
        }
    }
    
    @FXML
    private void desativarTransp(){
        TransportadoraService tServ = new TransportadoraService();
        
        try {
            if (idTransp == -1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma transportadora!");
                
                alert.show();
                return;
            }
            
            
            boolean desativado = tServ.desativarTransportadora(idTransp);
            
            if(desativado){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Transportadora desativada!");

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
        TransportadoraService tServ = new TransportadoraService();
        List<Transportadora> novaLista;
        try {
            novaLista = tServ.listarTransportadora();

            transpObsList.clear();
            transpObsList.addAll(novaLista);
   
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
