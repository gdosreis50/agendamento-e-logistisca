package Dto;

import java.util.List;

public class VeiculoDTO {

    private String placa;
    private String tipo;
    private int idfuncionario;
    private int tara;
    private List<VagaoDTO> vagoes;

    public List<VagaoDTO> getVagoes() {
        return vagoes;
    }

    public void setVagoes(List<VagaoDTO> vagoes) {
        this.vagoes = vagoes;
    }

    public int getTara() {
        return tara;
    }

    public void setTara(int tara) {
        this.tara = tara;
    }


    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(int idfuncionario) {
        this.idfuncionario = idfuncionario;
    }
    
    
}
