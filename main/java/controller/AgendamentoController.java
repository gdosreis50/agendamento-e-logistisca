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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projetotcc.App;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btViewAgendamento.setDisable(true);
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
