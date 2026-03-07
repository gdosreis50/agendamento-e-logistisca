package entidades;

import java.util.Date;


public class CheckList {
    private int idCheckList;
    private Date dataEmissao;
    private Veiculo veiculo;
    private Motorista motoristas;
    private Transportadora transportadora;
    private Funcionario funcionario;

    public int getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(int idCheckList) {
        this.idCheckList = idCheckList;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Motorista getMotorista() {
        return motoristas;
    }

    public void setMotorista(Motorista motorista) {
        this.motoristas = motorista;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
}
