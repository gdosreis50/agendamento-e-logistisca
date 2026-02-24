package Dto;

import java.util.Date;

public class AgendamentoDTO {
    private Date dataPrevista;
    private int idmotorista;
    private int idfuncionario;
    private int idpedido;
    private int idtransportadora;
    private int idveiculo;

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public int getIdmotorista() {
        return idmotorista;
    }

    public void setIdmotorista(int idmotorista) {
        this.idmotorista = idmotorista;
    }

    public int getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(int idfuncionario) {
        this.idfuncionario = idfuncionario;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdtransportadora() {
        return idtransportadora;
    }

    public void setIdtransportadora(int idtransportadora) {
        this.idtransportadora = idtransportadora;
    }

    public int getIdveiculo() {
        return idveiculo;
    }

    public void setIdveiculo(int idveiculo) {
        this.idveiculo = idveiculo;
    }
    
    
}
