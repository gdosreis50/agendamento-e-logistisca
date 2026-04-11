/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entidades.Agendamento;
import entidades.Motorista;
import entidades.Transportadora;
import entidades.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import service.AgendamentoService;
import service.MotoristaService;
import service.TransportadoraService;
import service.VeiculoService;
/**
 * FXML Controller class
 *
 * @author work & college
 */
public class AgendamentoController implements Initializable {


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
    @FXML
    private ComboBox<Veiculo> comboBoxVeiculo;
    @FXML
    private Label txtCompMin;
    @FXML
    private Label txtLargMin;
    @FXML
    private Label txtCintaMin;
    @FXML
    private Label txtqtdPaletevag1;
    @FXML
    private Label txtqtdPaletevag2;
    @FXML
    private Label txtqtdPaletevag3;
    @FXML
    private Label txtTaraTotal;
    @FXML
    private ComboBox<Transportadora> comboBoxTransp;
    @FXML
    private Label txtCnpjTransp;
    @FXML
    private Label txtAnttTransp;
    @FXML
    private Label txtEmailTransp;
    @FXML
    private CheckBox checkTranspPrivada;
    @FXML
    private ButtonBar barraBt;
    @FXML
    private Button btCheck;
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
    private Button btViewFuncionarios;
    @FXML
    private TableView<Agendamento> tabAgenda;
    @FXML
    private TableColumn<Agendamento, Integer> colunaID;
    @FXML
    private TableColumn<Agendamento, LocalDate> colunaData;
    @FXML
    private TableColumn<Agendamento, String> colunaNomeMot;
    @FXML
    private TableColumn<Agendamento, String> colunaPed;
    
    private List<Motorista> listaMotorista;
    private FilteredList<Motorista> listaFiltradaMotorista;
    
    private List<Veiculo> listaVeiculo;
    private FilteredList<Veiculo> listaFiltradaVeiculo;
    
    private List<Agendamento> agendados;
    private ObservableList<Agendamento> agendaObsList;
    
    private List<Transportadora> listaTransp;
    private FilteredList<Transportadora> listaFiltradaTransp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btViewAgendamento.setDisable(true);
        
        checkTranspPrivada.setSelected(false);
        
        
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
        
        
        AgendamentoService agenda = new AgendamentoService();
        
        try {


                agendados = agenda.listarAgendamento();

                colunaID = new TableColumn("ID");
                colunaID.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getIdAgendamento()));
                
                colunaData = new TableColumn("Data");
                colunaData.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getDataPrevista().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))));

                colunaNomeMot = new TableColumn("Motorista");
                colunaNomeMot.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getMotorista().getNomeMotorista()));

                colunaPed = new TableColumn("Veículo");
                colunaPed.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getPedido().getNumPed()));

                tabAgenda.getColumns().addAll(colunaID, colunaData, colunaNomeMot, colunaPed);
                
                
                agendaObsList = FXCollections.observableArrayList(agendados);
                
                tabAgenda.setItems(agendaObsList);
                
                
                
            
            } 
        catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        TransportadoraService transpServ = new TransportadoraService();
        
        try {
            listaTransp = transpServ.listarTransportadora();
            
            ObservableList<Transportadora> transpObsList = FXCollections.observableArrayList(listaTransp);
            
            listaFiltradaTransp = new FilteredList<>(transpObsList, p -> true);
            
            comboBoxTransp.setItems(listaFiltradaTransp);
            comboBoxTransp.setEditable(true);
            comboBoxTransp.setVisibleRowCount(5);
            
            comboBoxTransp.setConverter(new StringConverter<Transportadora>(){
                
                public String toString(Transportadora transportadora){
                    return transportadora == null ? "" : transportadora.getNomeTransportadora();
                }

                @Override
                public Transportadora fromString(String string) {
                    return comboBoxTransp.getItems().stream()
                            .filter(t -> t.getNomeTransportadora().equals(string))
                            .findFirst().orElse(null);
                }
            });
            
            comboBoxTransp.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                
                if(event.getCode() == KeyCode.SPACE){
                    
                    if(comboBoxTransp.getEditor().isFocused()){
                        IndexRange selection = comboBoxTransp.getEditor().getSelection();
                        
                        if(selection.getLength() > 0){
                            comboBoxTransp.getEditor().replaceText(selection, " ");
                        }
                    }
                }
            });
                        
            
            comboBoxTransp.getEditor().textProperty().addListener((obs,oldValue, newValue)->{
                
                Platform.runLater(() -> {
                    listaFiltradaTransp.setPredicate(transportadora -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    
                    return transportadora.getNomeTransportadora().toLowerCase().contains(newValue.toLowerCase());
                    
                });
                    
                if (comboBoxTransp.getEditor().isFocused() && !newValue.isEmpty()){
                    comboBoxTransp.show();
                }
                    
            });
        });
            
        comboBoxTransp.getEditor().setOnKeyReleased(event -> {
            if(event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN && event.getCode() != KeyCode.ENTER){
                comboBoxTransp.getEditor().positionCaret(comboBoxTransp.getEditor().getText().length());
            }
        });
            
        } 
        catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    @FXML
    private void limparCampos(ActionEvent event) {
        
    }

    @FXML
    private void agendarCarregamento(ActionEvent event) {
    }

    @FXML
    private void getPedido(ActionEvent event) {
    }

    @FXML
    private void preencherMot(ActionEvent event) {
    }

    @FXML
    private void preencherVeiculo(ActionEvent event) {
    }

    @FXML
    private void preencherTransp(ActionEvent event) {
    }

    @FXML
    private void alteraEstadoComboTransp(ActionEvent event) {
    }

    @FXML
    private void switchToChecklist(ActionEvent event) throws IOException {
        App.setRoot("telaInicial");
    }

    @FXML
    private void switchToTransportadora(ActionEvent event) throws IOException {
        App.setRoot("manutencaoTransportadora");
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
    private void switchToFuncionarios(ActionEvent event) throws IOException {
        App.setRoot("manutencaoFuncionario");
    }

    @FXML
    private void irParaFila(MouseEvent event) {
    }

}
