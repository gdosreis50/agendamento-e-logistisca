/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dto;

import java.util.Date;

/**
 *
 * @author work & college
 */
public class ChecklistDTO {
    private Date dataEmissao;
    private int idveiculo;
    private int idmotorista;
    private int idtransportadora;
    private int idfuncionario;

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public int getIdveiculo() {
        return idveiculo;
    }

    public void setIdveiculo(int idveiculo) {
        this.idveiculo = idveiculo;
    }

    public int getIdmotorista() {
        return idmotorista;
    }

    public void setIdmotorista(int idmotorista) {
        this.idmotorista = idmotorista;
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
    
    
}
