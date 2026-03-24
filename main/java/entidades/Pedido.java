package entidades;

import com.google.gson.annotations.SerializedName;


public class Pedido {
    @SerializedName("idpedidos")
    private int idPedido;
    private String numPed;
    private String nomeCliente;
    private String cidadeCliente;
    private int numSacos;
    private int numPaletes;
    private String numRomaneio;
    private String transportadora;
    private String statusPed;
    private int numPaleteRomaneio;
    private CheckList checkList;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNumPed() {
        return numPed;
    }

    public void setNumPed(String numPed) {
        this.numPed = numPed;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCidadeCliente() {
        return cidadeCliente;
    }

    public void setCidadeCliente(String cidadeCliente) {
        this.cidadeCliente = cidadeCliente;
    }

    public int getNumSacos() {
        return numSacos;
    }

    public void setNumSacos(int numSacos) {
        this.numSacos = numSacos;
    }

    public int getNumPaletes() {
        return numPaletes;
    }

    public void setNumPaletes(int numPaletes) {
        this.numPaletes = numPaletes;
    }

    public String getNumRomaneio() {
        return numRomaneio;
    }

    public void setNumRomaneio(String numRomaneio) {
        this.numRomaneio = numRomaneio;
    }

    public String getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(String transportadora) {
        this.transportadora = transportadora;
    }

    public String getStatusPed() {
        return statusPed;
    }

    public void setStatusPed(String statusPed) {
        this.statusPed = statusPed;
    }

    public int getNumPaleteRomaneio() {
        return numPaleteRomaneio;
    }

    public void setNumPaleteRomaneio(int numPaleteRomaneio) {
        this.numPaleteRomaneio = numPaleteRomaneio;
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }
    
    
}
