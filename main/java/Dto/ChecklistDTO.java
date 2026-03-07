/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;


/**
 *
 * @author work & college
 */
public class ChecklistDTO {
    private String numPed;
    private String dataEmissao;
    private int idveiculo;
    private int idmotoristas;
    private int idtransportadora;
    private int idfuncionario;

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getIdveiculo() {
        return idveiculo;
    }

    public void setIdveiculo(int idveiculo) {
        this.idveiculo = idveiculo;
    }

    public int getIdmotoristas() {
        return idmotoristas;
    }

    public void setIdmotoristas(int idmotorista) {
        this.idmotoristas = idmotorista;
    }

    public int getIdtransportadora() {
        return idtransportadora;
    }

    public void setIdtransportadora(int idtransportadora) {
        this.idtransportadora = idtransportadora;
    }

    public int getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(int idfuncionario) {
        this.idfuncionario = idfuncionario;
    }
    
    public String getNumPed() {
        return numPed;
    }

    public void setNumPed(String numPed) {
        this.numPed = numPed;
    }
    
}
