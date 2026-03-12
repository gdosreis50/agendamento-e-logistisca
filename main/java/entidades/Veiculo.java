package entidades;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Veiculo {
    
    @SerializedName("idveiculo")
    private int idVeiculo;
    private String placa;
    private String tipo;
    private Funcionario funcionario;
    @SerializedName("vagoes")
    private List<Vagao> vagoes;
    private int tara;

    public List<Vagao> getVagoes() {
        return vagoes;
    }

    public void setVagoes(List<Vagao> vagoes) {
        this.vagoes = vagoes;
    }


    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public int getTara() {
        return tara;
    }

    public void setTara(int tara) {
        this.tara = tara;
    }
    
    @Override
    public String toString(){
        return placa;
    }
    
}
