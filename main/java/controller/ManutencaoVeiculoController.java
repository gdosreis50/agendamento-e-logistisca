/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entidades.Funcionario;
import entidades.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projetotcc.App;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btViewVeiculo.setDisable(true);
    }    
    
    @FXML
    private void limparCampos(ActionEvent event) {
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
    }

    @FXML
    private void salvarVeiculo(ActionEvent event) {
    }

    @FXML
    private void desativarVeiculo(ActionEvent event) {
    }

    @FXML
    private void preencherMot(ActionEvent event) {
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
    }

}
