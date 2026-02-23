package entidades;


public class Vagao {
    
    private int idVagao;
    private double comprimento;
    private double largura;
    private double altura;
    private Veiculo veiculo;
    private Funcionario funcionario;

    public int getIdVagao() {
        return idVagao;
    }

    public void setIdVagao(int idVagao) {
        this.idVagao = idVagao;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    @Override
    public String toString(){
        return comprimento + " X " + largura + " X " + altura;
    }
    
    
}
