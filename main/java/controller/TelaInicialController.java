/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Dto.ChecklistDTO;
import entidades.CheckList;
import entidades.Funcionario;
import entidades.Motorista;
import entidades.Pedido;
import entidades.Vagao;
import entidades.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<CheckList> tabFila;
    @FXML
    private TableColumn<CheckList, Integer> colunaID;
    @FXML
    private TableColumn<CheckList, String> colunaNomeMot;
    @FXML
    private TableColumn<CheckList, String> colunaPlaca;
    
    private List<Funcionario> listaFuncionario; 
    private FilteredList<Funcionario> listaFiltradaFunc;
    
    private List<Motorista> listaMotorista;
    private FilteredList<Motorista> listaFiltradaMotorista;
    
    private List<Veiculo> listaVeiculo;
    private FilteredList<Veiculo> listaFiltradaVeiculo;
    
    private List<CheckList> fila;
    ObservableList<CheckList> checkObsList;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    
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
        
        
        ChecklistService check = new ChecklistService();
        
            try {


                fila = check.listarChecklists();

                colunaID = new TableColumn("ID");
                colunaID.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getIdCheckList()));

                colunaNomeMot = new TableColumn("Motorista");
                colunaNomeMot.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getMotorista().getNomeMotorista()));

                colunaPlaca = new TableColumn("Veículo");
                colunaPlaca.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getVeiculo().getPlaca()));

                tabFila.getColumns().addAll(colunaID, colunaNomeMot, colunaPlaca);
                checkObsList = FXCollections.observableArrayList(fila);
                
                tabFila.setItems(checkObsList);
                
            
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
            
            if (pedido.getNumPaletes() != 0){
            
                int tara = pedido.getNumPaletes();
                int cintas = pedido.getNumPaletes();
                double comprimento = pedido.getNumPaletes();
                final double LARGURA = 2.5;
                
                //System.out.println(LARGURA);
                
                comprimento = (comprimento/2) * 1.05;
                cintas = (cintas/2) + 2;
                tara = tara * 1000;
                
                String compFormatado = String.format("%.2f", comprimento);
                String largFormatada = String.format("%.2f", LARGURA);
            
                txtCliente.setText(pedido.getNomeCliente());
                txtLiberado.setText(pedido.getStatusPed());
                txtNumPaletes.setText(String.valueOf(pedido.getNumPaletes()));
                txtCalcTaraMin.setText(String.valueOf(tara));
                txtTransp.setText(pedido.getTransportadora());
                txtTipoProduto.setText("Carolina Soil");
                txtLargMin.setText(largFormatada);
                txtCompMin.setText(compFormatado);
                txtCintaMin.setText(Integer.toString(cintas));
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
                txtTipoVeic.setText("");
                txtPlaca.setText("");
                txtPrimVagao.setText("");
                txtSegVagao.setText("");
                txtTercVagao.setText("");
                txtCpfMot.setText("");
                txtCnhMot.setText("");
                txtCatCnh.setText("");
                txtVencChn.setText("");
                txtTel.setText("");
                txtCompMin.setText("");
                txtLargMin.setText("");
                txtCintaMin.setText("");
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
                txtTipoVeic.setText("");
                txtPlaca.setText("");
                txtPrimVagao.setText("");
                txtSegVagao.setText("");
                txtTercVagao.setText("");
                txtCpfMot.setText("");
                txtCnhMot.setText("");
                txtCatCnh.setText("");
                txtVencChn.setText("");
                txtTel.setText("");
                txtCompMin.setText("");
                txtLargMin.setText("");
                txtCintaMin.setText("");
                comboBoxMotorista.setValue(null);
                comboBoxFuncionario.setValue(null);
                comboBoxVeiculo.setValue(null);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Algo deu muito errado! \n Coloque um Pedido Valido, Seu nome, Motorista e Veículo!");
                
                alert.show();
                
                txtFieldNumPedido.setText("");
                txtTipoProduto.setText("");
                txtCliente.setText("");
                txtLiberado.setText("");
                txtNumPaletes.setText("");
                txtCalcTaraMin.setText("");
                txtTransp.setText("");
                txtTipoVeic.setText("");
                txtPlaca.setText("");
                txtPrimVagao.setText("");
                txtSegVagao.setText("");
                txtTercVagao.setText("");
                txtCpfMot.setText("");
                txtCnhMot.setText("");
                txtCatCnh.setText("");
                txtVencChn.setText("");
                txtTel.setText("");
                txtCompMin.setText("");
                txtLargMin.setText("");
                txtCintaMin.setText("");
                comboBoxMotorista.setValue(null);
                comboBoxFuncionario.setValue(null);
                comboBoxVeiculo.setValue(null);
        }
        
        
        
    }
    
    @FXML
    private void preencherMot (){
        
        if(comboBoxMotorista.getValue() != null){
            Motorista motorista = comboBoxMotorista.getValue();
            
            txtCpfMot.setText(motorista.getCpf());
            txtCnhMot.setText(motorista.getCnh());
            txtCatCnh.setText(motorista.getCategoriaCnh());        
            txtVencChn.setText(motorista.getDataVencimentoCnh().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
            txtTel.setText(motorista.getTelefone());
        }else{
            txtCpfMot.setText("");
            txtCnhMot.setText("");
            txtCatCnh.setText("");
            txtVencChn.setText("");
            txtTel.setText("");
        }
        
        
    }
    
    @FXML
    private void preencherVeiculo (){
        if(comboBoxVeiculo.getValue() != null){
            
            Veiculo veiculo = comboBoxVeiculo.getValue();
            
            txtPlaca.setText(veiculo.getPlaca());
            txtTipoVeic.setText(veiculo.getTipo());
            
            Vagao vagao1 = veiculo.getVagoes().get(0);    
            txtPrimVagao.setText(vagao1.toString());
            double comp1 = vagao1.getComprimento();
            double larg1 = vagao1.getLargura();
            int tara = veiculo.getTara();
            txtqtdPaletevag1.setText(qtdPalete(comp1, larg1) + " Paletes");
            
            Vagao vagao2 = veiculo.getVagoes().get(1);    
            txtSegVagao.setText(vagao2.toString());
            double comp2 = vagao2.getComprimento();
            double larg2 = vagao2.getLargura();
            
            txtqtdPaletevag2.setText(qtdPalete(comp2, larg2) + " Paletes");
            
            Vagao vagao3 = veiculo.getVagoes().get(2);    
            txtTercVagao.setText(vagao3.toString());
            double comp3 = vagao3.getComprimento();
            double larg3 = vagao3.getLargura();
            
            txtqtdPaletevag3.setText(qtdPalete(comp3, larg3) + " Paletes");
            
            txtTaraTotal.setText(Integer.toString(tara));
            
            }else{
            txtTipoVeic.setText("");
            txtPrimVagao.setText("");
            txtSegVagao.setText("");
            txtTercVagao.setText("");
            txtPlaca.setText("");
            txtqtdPaletevag1.setText("");
            txtqtdPaletevag2.setText("");
            txtqtdPaletevag3.setText("");
            txtTaraTotal.setText("");
        }
            
            
        }
    
    @FXML
    private void limparCampos (){
        txtFieldNumPedido.setText("");
                txtTipoProduto.setText("");
                txtCliente.setText("");
                txtLiberado.setText("");
                txtNumPaletes.setText("");
                txtCalcTaraMin.setText("");
                txtTransp.setText("");
                txtTipoVeic.setText("");
                txtPlaca.setText("");
                txtPrimVagao.setText("");
                txtSegVagao.setText("");
                txtTercVagao.setText("");
                txtCpfMot.setText("");
                txtCnhMot.setText("");
                txtCatCnh.setText("");
                txtVencChn.setText("");
                txtTel.setText("");
                txtCompMin.setText("");
                txtLargMin.setText("");
                txtCintaMin.setText("");
                txtqtdPaletevag1.setText("");
                txtqtdPaletevag2.setText("");
                txtqtdPaletevag3.setText("");
                txtTaraTotal.setText("");
                comboBoxMotorista.setValue(null);
                comboBoxFuncionario.setValue(null);
                comboBoxVeiculo.setValue(null);
    }

    private String qtdPalete (Double comp, Double larg){
        Double qtdCompToInt = (comp/1.05);
        int comprimento = qtdCompToInt.intValue();
        
        Double qtdLargToInt = (larg/1.25);
        int largura = qtdLargToInt.intValue();
        
        int qtdDimensao = comprimento * largura;
        
        String resposta = Integer.toString(qtdDimensao);
        
        return resposta;
    } 
    @FXML
    private void sairDaFila (){
        
        CheckList checkList = tabFila.getSelectionModel().getSelectedItem();
        int idCheck = checkList.getIdCheckList();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sistema");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que quer fechar este carregamento?");
             
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            ChecklistService checklistService = new ChecklistService();
            try {
                boolean ok = checklistService.confirmaCarregamento(idCheck);
                
                if(ok){
                    Alert alertConf = new Alert(Alert.AlertType.INFORMATION);
                    alertConf.setTitle("Sistema");
                    alertConf.setHeaderText(null);
                    alertConf.setContentText("Carregamento retirado da fila!");
                    
                    alertConf.show();
                    
                    atualizaFila();
                }else{
                    Alert alertConf = new Alert(Alert.AlertType.INFORMATION);
                    alertConf.setTitle("Sistema");
                    alertConf.setHeaderText(null);
                    alertConf.setContentText("Algo deu errado!");
                    
                    alertConf.show();
                }
                
            } catch (Exception ex) {
                Alert alertConf = new Alert(Alert.AlertType.INFORMATION);
                alertConf.setTitle("Sistema");
                alertConf.setHeaderText(null);
                alertConf.setContentText("Algo deu muito errado!");
                    
                alertConf.show();
                Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
        
        
        
    }
    
    private void atualizaFila(){
  
        ChecklistService check = new ChecklistService();
        
        try {
            List<CheckList> novaLista = check.listarChecklists();
            checkObsList.clear();
            checkObsList.addAll(novaLista);
        } catch (Exception ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
}