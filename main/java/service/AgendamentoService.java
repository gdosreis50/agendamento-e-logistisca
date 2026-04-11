package service;

import Dto.AgendamentoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.Agendamento;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import util.LocalDateAdapter;

public class AgendamentoService {
    public static String BASE_URL = "http://localhost/api/agendamento/";

    
    //retorna uma lista do tipo Agendamento com todas od agendamentos ativos no BD
    public static List<Agendamento> listarAgendamento() throws Exception{

        HttpRequest getListAgendamento = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/agendamento"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListAgendamento, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        Type listType = new TypeToken<List<Agendamento>>(){}.getType();
        
        List<Agendamento> lista = new ArrayList<Agendamento>();
        
        lista = gson.fromJson(getResposta.body(), listType);
        
        if (lista == null){
            return Collections.EMPTY_LIST;
        }else{
            return lista;
        }
        
        //String resposta = getResposta.body();
    }
    
    //Retorna um objeto tipo Agendamento a partir de seu ID
    public static Agendamento getAgendamento(int id) throws Exception{
        
            HttpRequest getAgendamento = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/agendamento/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getAgendamento, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        
        Type agendamento = new TypeToken<Agendamento>(){}.getType();
        
        return gson.fromJson(getResposta.body(), agendamento);
    }
    
    
    // Cria novo agendamento no BD e retorna true se der certo ou false para falha
    public static boolean novoAgendamento(Date dataPrevista, int idMotorista, int idPedido, int idTransportadora, int idVeiculo, int idFunc) throws Exception{
        
        
        AgendamentoDTO agendamento = new AgendamentoDTO();
        
        agendamento.setDataPrevista(dataPrevista);
        agendamento.setIdmotorista(idMotorista);
        agendamento.setIdfuncionario(idFunc);
        agendamento.setIdpedido(idPedido);
        agendamento.setIdtransportadora(idTransportadora);
        agendamento.setIdveiculo(idVeiculo);
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String request = gson.toJson(agendamento);
        
        //System.out.println(request);
        
        
        HttpRequest novoAgendamento;
        novoAgendamento = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novoAgendamentoResposta = httpClient.send(novoAgendamento, HttpResponse.BodyHandlers.ofString());
        int httpCode = novoAgendamentoResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
    
    // Atualiza dado agendamento. Retorna true se bem sucedido e false para falha
    public static boolean atualizaAgendamento(int idAgendamento, Date dataPrevista, int idMotorista, int idPedido, int idTransportadora, int idVeiculo, int idFunc) throws Exception{
        
        AgendamentoDTO agendamento = new AgendamentoDTO();
        
        agendamento.setDataPrevista(dataPrevista);
        agendamento.setIdmotorista(idMotorista);
        agendamento.setIdfuncionario(idFunc);
        agendamento.setIdpedido(idPedido);
        agendamento.setIdtransportadora(idTransportadora);
        agendamento.setIdveiculo(idVeiculo);
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String request = gson.toJson(agendamento);
        
        //System.out.print(request);
        
        HttpRequest atualizaAgendamento = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/agendamento/" + idAgendamento))
                .PUT(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> atualizaAgendamentoResposta = httpClient.send(atualizaAgendamento, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = atualizaAgendamentoResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
    
    //Soft delete agendamento. Retorna True se bem sucedido e false para falha
    public static boolean desativarAgendamento(int idAgendamento) throws Exception{
        
        HttpRequest desativaAgendamento = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/agendamento/" + idAgendamento))
                .DELETE()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> desativaAgendamentoResposta = httpClient.send(desativaAgendamento, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = desativaAgendamentoResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
}
