
package Dto;

import java.util.Date;

public class MotoristaDTO {
    
    private String nomeMotorista;
    private String cpf;
    private String cnh;
    private Date dataVencimentoCnh;
    private String categoriaCnh;

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public Date getDataVencimentoCnh() {
        return dataVencimentoCnh;
    }

    public void setDataVencimentoCnh(Date dataVencimentoCnh) {
        this.dataVencimentoCnh = dataVencimentoCnh;
    }

    public String getCategoriaCnh() {
        return categoriaCnh;
    }

    public void setCategoriaCnh(String categoriaCnh) {
        this.categoriaCnh = categoriaCnh;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdfuncionario() {
        return idfuncionario;
    }

    public void setIdfuncionario(int idfuncionario) {
        this.idfuncionario = idfuncionario;
    }
    private String telefone;
    private int idfuncionario;
}
