/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Dto.FuncionarioDTO;
import entidades.Funcionario;
import entidades.Motorista;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
/**
 * FXML Controller class
 *
 * @author work & college
 */
public class ManutencaoFuncionarioController implements Initializable {


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
    private Button btViewMotorista;
    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;
    @FXML
    private TextField textFieldNomeFunc;
    @FXML
    private TextField textFieldCpfFunc;
    @FXML
    private TableView<Funcionario> tabelaFunc;
    @FXML
    private TableColumn<Funcionario, String> colunaNome;
    
    private boolean novo;
    private int idFunc;
    
    private List<Funcionario> listaFuncionario;
    private FilteredList<Funcionario> listaFiltradaFunc;
    
    private ObservableList<Funcionario> obsList;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        novo = true;
        idFunc = -1;
        
        FuncionarioService funcionarios = new FuncionarioService();
        
        try {
            
            listaFuncionario = funcionarios.listarFuncionarios();

            obsList = FXCollections.observableArrayList(listaFuncionario);
            
            colunaNome = new TableColumn("Nome");
            colunaNome.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNomeFunc()));
            
            tabelaFunc.getColumns().addAll(colunaNome);
            
            tabelaFunc.setItems(obsList);
            
            
            
            
            
            listaFiltradaFunc = new FilteredList<>(obsList, p -> true);
            
            comboBoxFuncionario.setItems(listaFiltradaFunc); // comboBox está nulo (???????????)
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
        
        
        
    }    
    
    @FXML
    private void limparCampos() {
        idFunc = -1;
        textFieldNomeFunc.setText("");
        textFieldCpfFunc.setText("");
        comboBoxFuncionario.setValue(null);
        
        novo = true;
    }

    @FXML
    private void salvarFunc(ActionEvent event) {
        if (textFieldNomeFunc.getText() == null || textFieldCpfFunc.getText() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
                
            alert.show();
            return;
        }
        
        
        if(novo){
            FuncionarioDTO funcDTO = new FuncionarioDTO();
        
            funcDTO.setNomeFunc(textFieldNomeFunc.getText());
            funcDTO.setCpf(textFieldCpfFunc.getText());

            FuncionarioService funcService = new FuncionarioService();

            try {
                boolean salvo = funcService.novoFuncionario(funcDTO);

                if (salvo){
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
                Logger.getLogger(ManutencaoFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu muito errado! Verifique os campos e se persistir, contate o TI!");

                alert.show();
                limparCampos();            
            }
        }else{
            FuncionarioDTO funcDTO = new FuncionarioDTO();
        
            funcDTO.setNomeFunc(textFieldNomeFunc.getText());
            funcDTO.setCpf(textFieldCpfFunc.getText());

            FuncionarioService funcService = new FuncionarioService();

            try {
                boolean salvo = funcService.atualizaFuncionario(idFunc, funcDTO);

                if (salvo){
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
                Logger.getLogger(ManutencaoFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void switchToPedidos(ActionEvent event) throws IOException {
        App.setRoot("visualizacaoPedidos");
    }

    @FXML
    private void switchToMotorista(ActionEvent event) throws IOException {
        App.setRoot("manutencaoMotorista");
    }

    @FXML
    private void preencherFunc(ActionEvent event) {
        if(comboBoxFuncionario.getValue() != null){
            Funcionario funcionario = comboBoxFuncionario.getValue();
            
            idFunc = funcionario.getIdFuncionario();
            
            textFieldNomeFunc.setText(funcionario.getNomeFunc());
            textFieldCpfFunc.setText(funcionario.getCpf());
            
            novo = false;
        }else{
            textFieldNomeFunc.setText("");
            textFieldCpfFunc.setText("");
        }
    }

    @FXML
    private void carregaDadosDaTabela(MouseEvent event) {
        if(event.getClickCount() == 2){
            Funcionario funcionario = tabelaFunc.getSelectionModel().getSelectedItem();
            idFunc = funcionario.getIdFuncionario();
            
            textFieldNomeFunc.setText(funcionario.getNomeFunc());
            textFieldCpfFunc.setText(funcionario.getCpf());
            
            
            novo = false;
        }
    }
    
    @FXML
    private void desativaFunc(){
        if (idFunc == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um Motorista");
                
            alert.show();
            return;
        }
        FuncionarioService f = new FuncionarioService();
        
        try {
            boolean desativado = f.desativarFuncionario(idFunc);
            
            if(desativado){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Desativado com sucesso!");
                
                alert.show();
                atualizarLista();
                limparCampos();
                return;
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu errado");
                
                alert.show();
                limparCampos();
                return;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ManutencaoFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sistema");
            alert.setHeaderText(null);
            alert.setContentText("Algo deu muito errado! Verifique os campos e se persistir, contate o TI!");

            alert.show();
            limparCampos(); 
        }
        
    }
    
    private void atualizarLista(){
        FuncionarioService func = new FuncionarioService();
        List<Funcionario> novaLista;
        try {
            novaLista = func.listarFuncionarios();

            obsList.clear();
            obsList.addAll(novaLista);
   
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
