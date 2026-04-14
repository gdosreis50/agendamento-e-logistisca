package service;

import Dto.VagaoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.Vagao;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import util.LocalDateAdapter;

public class VagaoService {
    public static String BASE_URL = "http://localhost/api/vagao/";

    
    //retorna uma lista do tipo Vagao com todos os vagoes ativos no BD
    public static List<Vagao> listarVagao() throws Exception{

        HttpRequest getListVagao = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/vagao"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListVagao, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Vagao>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo Vagao a partir de seu ID
    public static Vagao getVagao(int id) throws Exception{
        
            HttpRequest getVagao = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/vagao/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getVagao, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type vagao = new TypeToken<Vagao>(){}.getType();
        
        return gson.fromJson(getResposta.body(), vagao);
    }
    
    
    // Cria novo vagao no BD e retorna true se der certo ou false para falha
    public static boolean novoVagao(VagaoDTO vagao) throws Exception{

        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String request = gson.toJson(vagao);
        
        //System.out.println(request);
        
        
        HttpRequest novoVagao;
        novoVagao = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novoVagaoResposta = httpClient.send(novoVagao, HttpResponse.BodyHandlers.ofString());
        int httpCode = novoVagaoResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
    
    // Atualiza dado vagao. Retorna true se bem sucedido e false para falha
    public static boolean atualizaVagao(int idVagao, double comprimento, double largura, double altura, int idVeiculo, int idFunc) throws Exception{
        
        VagaoDTO vagao = new VagaoDTO();
        
        vagao.setComprimento(comprimento);
        vagao.setLargura(largura);
        vagao.setAltura(altura);
        vagao.setIdVeiculo(idVeiculo);
        vagao.setIdFuncionario(idFunc);
        
        Gson gson = new Gson();
        String request = gson.toJson(vagao);
        
        //System.out.print(request);
        
        HttpRequest atualizaVagao = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/vagao/" + idVagao))
                .PUT(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> atualizaVagaoResposta = httpClient.send(atualizaVagao, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = atualizaVagaoResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
    
    //Soft delete vagao. Retorna True se bem sucedido e false para falha
    public static boolean desativarVagao(int idVagao) throws Exception{
        
        HttpRequest desativaVagao = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/vagao/" + idVagao))
                .DELETE()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> desativaVagaoResposta = httpClient.send(desativaVagao, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = desativaVagaoResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
}
