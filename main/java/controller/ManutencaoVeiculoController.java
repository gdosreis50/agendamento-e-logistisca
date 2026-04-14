/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Dto.VagaoDTO;
import Dto.VeiculoDTO;
import entidades.Funcionario;
import entidades.Vagao;
import entidades.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.control.CheckBox;
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
import service.VeiculoService;
/**
 * FXML Controller class
 *
 * @author work & college
 */
public class ManutencaoVeiculoController implements Initializable {


    @FXML
    private ComboBox<Veiculo> comboBoxVeiculo;
    @FXML
    private ComboBox<Funcionario> comboBoxFuncionario;
    @FXML
    private TextField textFieldPlaca;
    @FXML
    private TextField textFieldTipo;
    @FXML
    private TextField txtFieldTara;
    @FXML
    private TextField textFieldCompPrim;
    @FXML
    private TextField txtFieldLarguraPrim;
    @FXML
    private CheckBox checkCarroceriaFechada;
    @FXML
    private TextField txtFieldAlturaPrim;
    @FXML
    private TextField textFieldCompSeg;
    @FXML
    private TextField txtFieldLarguraSeg;
    @FXML
    private TextField txtFieldAlturaSeg;
    @FXML
    private CheckBox checkDoisVagoes;
    @FXML
    private TextField txtFieldAlturaTerc;
    @FXML
    private TextField txtFieldLarguraTerc;
    @FXML
    private TextField textFieldCompTerc;
    @FXML
    private CheckBox checkTresVagoes;
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
    private TableView<Veiculo> tabelaVeiculo;
    @FXML
    private TableColumn<Veiculo, Integer> colunaID;
    @FXML
    private TableColumn<Veiculo, String> colunaPlaca;
    @FXML
    private TableColumn<Veiculo, String> colunaTipo;
    
    private List<Funcionario> listaFuncionario;
    private FilteredList<Funcionario> listaFiltradaFunc;
    
    private List<Veiculo> listaVeiculo;
    private FilteredList<Veiculo> listaFiltradaVeiculo;
    private ObservableList<Veiculo> veiObsList;
    
    private Integer idVeiculo;
    private boolean novo;
    
    NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btViewVeiculo.setDisable(true);
        novo = true;
        idVeiculo = null;
        
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
        
        VeiculoService veiculos = new VeiculoService();
        
        try {
            
            listaVeiculo = veiculos.listarVeiculo();
            
            veiObsList = FXCollections.observableArrayList(listaVeiculo);
            
            listaFiltradaVeiculo = new FilteredList<>(veiObsList, p -> true);
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
        
        colunaID = new TableColumn("ID");
        colunaID.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getIdVeiculo()));
        
        colunaPlaca = new TableColumn("Placa");
        colunaPlaca.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getPlaca()));
        
        colunaTipo = new TableColumn("Tipo");
        colunaTipo.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getTipo()));
        
        tabelaVeiculo.getColumns().addAll(colunaID, colunaPlaca, colunaTipo);
        
        veiObsList = FXCollections.observableArrayList(listaVeiculo);
        
        tabelaVeiculo.setItems(veiObsList);
        
    }    
    
    @FXML
    private void limparCampos() {
        textFieldPlaca.setText("");
        textFieldTipo.setText("");
        txtFieldTara.setText("");
        comboBoxVeiculo.setValue(null);
        comboBoxFuncionario.setValue(null);
        checkCarroceriaFechada.setSelected(false);
        textFieldCompPrim.setText("");
        txtFieldLarguraPrim.setText("");
        txtFieldAlturaPrim.setText("");
        checkDoisVagoes.setSelected(false);
        textFieldCompSeg.setText("");
        txtFieldLarguraSeg.setText("");
        txtFieldAlturaSeg.setText("");
        checkTresVagoes.setSelected(false);
        textFieldCompTerc.setText("");
        txtFieldLarguraTerc.setText("");
        txtFieldAlturaTerc.setText("");
        novo = true;
        idVeiculo = null;
    }

    @FXML
    private void salvarVeiculo(ActionEvent event) throws ParseException {
        
        if(novo){
            if(comboBoxFuncionario.getValue() == null || textFieldPlaca.getText() == null || txtFieldTara.getText() == null || txtFieldLarguraPrim.getText() == null || textFieldCompPrim.getText() == null){
            
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Coloque Seu nome e preencha todos os campos! \n Caso seu nome não esteja disponível, contate o TI.");

                alert.show();
                return;
            }
        
            

            List<VagaoDTO> listaVagao = new ArrayList<>();
            VagaoDTO vagao1 = new VagaoDTO();

            vagao1.setComprimento(nf.parse(textFieldCompPrim.getText()).doubleValue());
            vagao1.setLargura(nf.parse(txtFieldLarguraPrim.getText()).doubleValue());
            if(checkCarroceriaFechada.isSelected()){
                vagao1.setAltura(nf.parse(txtFieldAlturaPrim.getText()).doubleValue());
            }
            vagao1.setIdFuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            listaVagao.add(vagao1);

            if(checkDoisVagoes.isSelected()){
                VagaoDTO vagao2 = new VagaoDTO();

                vagao2.setComprimento(nf.parse(textFieldCompSeg.getText()).doubleValue());
                vagao2.setLargura(nf.parse(txtFieldLarguraSeg.getText()).doubleValue());
                if(checkCarroceriaFechada.isSelected()){
                    vagao2.setAltura(nf.parse(txtFieldAlturaSeg.getText()).doubleValue());
                }
                vagao2.setIdFuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
                listaVagao.add(vagao2);
            }

            if(checkTresVagoes.isSelected()){
                VagaoDTO vagao3 = new VagaoDTO();

                vagao3.setComprimento(nf.parse(textFieldCompTerc.getText()).doubleValue());
                vagao3.setLargura(nf.parse(txtFieldLarguraTerc.getText()).doubleValue());
                if(checkCarroceriaFechada.isSelected()){
                    vagao3.setAltura(nf.parse(txtFieldAlturaTerc.getText()).doubleValue());
                }
                vagao3.setIdFuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
                listaVagao.add(vagao3);
            }


            VeiculoDTO veiculoDTO = new VeiculoDTO();

            veiculoDTO.setPlaca(textFieldPlaca.getText());
            veiculoDTO.setTipo(textFieldTipo.getText());
            veiculoDTO.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            veiculoDTO.setTara(Integer.parseInt(txtFieldTara.getText()));
            veiculoDTO.setVagoes(listaVagao);


            try {
                VeiculoService vs = new VeiculoService();
                boolean salvo = vs.novoVeiculo(veiculoDTO);

                if (salvo){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sistema");
                            alert.setHeaderText(null);
                            alert.setContentText("Veículo Salvo");

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
                Logger.getLogger(ManutencaoVeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            if(idVeiculo == null || comboBoxFuncionario.getValue() == null || textFieldPlaca.getText() == null || txtFieldTara.getText() == null || txtFieldLarguraPrim.getText() == null || textFieldCompPrim.getText() == null){
            
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Coloque Seu nome e preencha todos os campos! \n Caso seu nome não esteja disponível, contate o TI.");

                alert.show();
                return;
            }
        
            NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

            List<VagaoDTO> listaVagao = new ArrayList<>();
            VagaoDTO vagao1 = new VagaoDTO();

            vagao1.setComprimento(nf.parse(textFieldCompPrim.getText()).doubleValue());
            vagao1.setLargura(nf.parse(txtFieldLarguraPrim.getText()).doubleValue());
            if(checkCarroceriaFechada.isSelected()){
                vagao1.setAltura(nf.parse(txtFieldAlturaPrim.getText()).doubleValue());
            }
            vagao1.setIdFuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            vagao1.setIdVeiculo(idVeiculo);
            listaVagao.add(vagao1);

            if(checkDoisVagoes.isSelected()){
                VagaoDTO vagao2 = new VagaoDTO();

                vagao2.setComprimento(nf.parse(textFieldCompSeg.getText()).doubleValue());
                vagao2.setLargura(nf.parse(txtFieldLarguraSeg.getText()).doubleValue());
                if(checkCarroceriaFechada.isSelected()){
                    vagao2.setAltura(nf.parse(txtFieldAlturaSeg.getText()).doubleValue());
                }
                vagao2.setIdFuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
                vagao2.setIdVeiculo(idVeiculo);
                listaVagao.add(vagao2);
            }

            if(checkTresVagoes.isSelected()){
                VagaoDTO vagao3 = new VagaoDTO();

                vagao3.setComprimento(nf.parse(textFieldCompTerc.getText()).doubleValue());
                vagao3.setLargura(nf.parse(txtFieldLarguraTerc.getText()).doubleValue());
                if(checkCarroceriaFechada.isSelected()){
                    vagao3.setAltura(nf.parse(txtFieldAlturaTerc.getText()).doubleValue());
                }
                vagao3.setIdFuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
                vagao3.setIdVeiculo(idVeiculo);
                listaVagao.add(vagao3);
            }


            VeiculoDTO veiculoDTO = new VeiculoDTO();

            veiculoDTO.setPlaca(textFieldPlaca.getText());
            veiculoDTO.setTipo(textFieldTipo.getText());
            veiculoDTO.setIdfuncionario(comboBoxFuncionario.getValue().getIdFuncionario());
            veiculoDTO.setTara(Integer.parseInt(txtFieldTara.getText()));
            veiculoDTO.setVagoes(listaVagao);


            try {
                VeiculoService vs = new VeiculoService();
                boolean salvo = vs.atualizaVeiculo(idVeiculo, veiculoDTO);

                if (salvo){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sistema");
                            alert.setHeaderText(null);
                            alert.setContentText("Veículo Atualizado");

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
                Logger.getLogger(ManutencaoVeiculoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }

    @FXML
    private void desativarVeiculo(ActionEvent event) {
        VeiculoService vServ = new VeiculoService();
        
        try {
            if (idVeiculo == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Selecione um veículo!");
                
                alert.show();
                return;
            }
            
            
            boolean desativado = vServ.desativarVeiculo(idVeiculo);
            
            if(desativado){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sistema");
                alert.setHeaderText(null);
                alert.setContentText("Veículo desativado!");

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

    @FXML
    private void preencherVeiculo(ActionEvent event) {
        if(comboBoxVeiculo.getValue() != null){
            
            Veiculo veiculo = comboBoxVeiculo.getValue();
            
            textFieldPlaca.setText(veiculo.getPlaca());
            textFieldTipo.setText(veiculo.getTipo());
            txtFieldTara.setText(Integer.toString(veiculo.getTara()));
            
    
            List<Vagao> vagoes = veiculo.getVagoes();
            
    
            if(vagoes.size() > 0){
                Vagao vagao1 = veiculo.getVagoes().get(0);    
                textFieldCompPrim.setText(String.format(new Locale("pt", "br"), "%.2f", vagao1.getComprimento()));
                txtFieldLarguraPrim.setText(String.format(new Locale("pt", "br"), "%.2f", vagao1.getLargura()));
                txtFieldAlturaPrim.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao1.getAltura()));
            }
            
            if(vagoes.size() > 1){
                Vagao vagao2 = veiculo.getVagoes().get(1);    
                textFieldCompSeg.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao2.getComprimento()));
                txtFieldLarguraSeg.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao2.getLargura()));
                txtFieldAlturaSeg.setText(String.format(new Locale("pt", "BR"), "%.2f",vagao2.getAltura()));
                checkDoisVagoes.setSelected(true);
            }
            
            if(vagoes.size() > 2){
                veiculo.getVagoes().get(2);
                Vagao vagao3 = veiculo.getVagoes().get(2);    
                textFieldCompTerc.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao3.getComprimento()));
                txtFieldLarguraTerc.setText(String.format(new Locale("pt", "BR"), "%.2f",vagao3.getLargura()));
                txtFieldAlturaTerc.setText(String.format(new Locale("pt", "BR"), "%.2f",vagao3.getAltura()));
                checkTresVagoes.setSelected(true);
            }
            
            novo = false;
            
        }else{
            textFieldTipo.setText("");
            textFieldCompPrim.setText("");
            txtFieldLarguraPrim.setText("");
            txtFieldAlturaPrim.setText("");
            textFieldCompSeg.setText("");
            txtFieldLarguraSeg.setText("");
            txtFieldAlturaSeg.setText("");
            textFieldCompTerc.setText("");
            txtFieldLarguraTerc.setText("");
            txtFieldAlturaTerc.setText("");
            textFieldPlaca.setText("");
            txtFieldTara.setText("");
        }
    }

    @FXML
    private void switchToCheck(ActionEvent event) throws IOException {
        App.setRoot("telaInicial");
    }

    @FXML
    private void switchToTransportadora(ActionEvent event) throws IOException {
        App.setRoot("manutencaoTransportadora");
    }

    @FXML
    private void switchToAgendamento(ActionEvent event) throws IOException {
        App.setRoot("agendamento");
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
    private void carregaDadosDaTabela(MouseEvent event) {
        if(event.getClickCount() == 2){
            Veiculo veiculo = tabelaVeiculo.getSelectionModel().getSelectedItem();
            
            idVeiculo = veiculo.getIdVeiculo();
            
            textFieldPlaca.setText(veiculo.getPlaca());
            textFieldTipo.setText(veiculo.getTipo());
            txtFieldTara.setText(Integer.toString(veiculo.getTara()));
            
    
            List<Vagao> vagoes = veiculo.getVagoes();
            
    
            if(vagoes.size() > 0){
                Vagao vagao1 = veiculo.getVagoes().get(0);    
                textFieldCompPrim.setText(String.format(new Locale("pt", "br"), "%.2f", vagao1.getComprimento()));
                txtFieldLarguraPrim.setText(String.format(new Locale("pt", "br"), "%.2f", vagao1.getLargura()));
                txtFieldAlturaPrim.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao1.getAltura()));
            }
            
            if(vagoes.size() > 1){
                Vagao vagao2 = veiculo.getVagoes().get(1);    
                textFieldCompSeg.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao2.getComprimento()));
                txtFieldLarguraSeg.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao2.getLargura()));
                txtFieldAlturaSeg.setText(String.format(new Locale("pt", "BR"), "%.2f",vagao2.getAltura()));
                checkDoisVagoes.setSelected(true);
            }
            
            if(vagoes.size() > 2){
                veiculo.getVagoes().get(2);
                Vagao vagao3 = veiculo.getVagoes().get(2);    
                textFieldCompTerc.setText(String.format(new Locale("pt", "BR"), "%.2f", vagao3.getComprimento()));
                txtFieldLarguraTerc.setText(String.format(new Locale("pt", "BR"), "%.2f",vagao3.getLargura()));
                txtFieldAlturaTerc.setText(String.format(new Locale("pt", "BR"), "%.2f",vagao3.getAltura()));
                checkTresVagoes.setSelected(true);
            }
            
            novo = false;
        }
    }
    
    private void atualizarLista(){
        VeiculoService veic = new VeiculoService();
        List<Veiculo> novaLista;
        try {
            novaLista = veic.listarVeiculo();

            veiObsList.clear();
            veiObsList.addAll(novaLista);
            
        } catch (Exception ex) {
            Logger.getLogger(ManutencaoFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
