package service;

import Dto.VeiculoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.Veiculo;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import util.LocalDateAdapter;

public class VeiculoService {
    
    public static String BASE_URL = "http://localhost/api/veiculo";
    
    //retorna uma lista do tipo veiculo com todos os veiculos ativos no BD
    public static List<Veiculo> listarVeiculo() throws Exception{

        HttpRequest getListVeiculo = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/veiculo"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListVeiculo, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        Type listType = new TypeToken<List<Veiculo>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo Veiculo a partir de seu ID
    public static Veiculo getVeiculo(int id) throws Exception{
        
            HttpRequest getListVeiculo = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/veiculo/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListVeiculo, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type veiculo = new TypeToken<Veiculo>(){}.getType();
        
        return gson.fromJson(getResposta.body(), veiculo);
    }
    
    // Cria novo veiculo no BD e retorna true se der certo ou false para falha
    public static boolean novoVeiculo(VeiculoDTO veiculo) throws Exception{
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String request = gson.toJson(veiculo);
        
        //System.out.println(request);
        
        
        HttpRequest novoVeiculo;
        novoVeiculo = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novoVeiculoResposta = httpClient.send(novoVeiculo, HttpResponse.BodyHandlers.ofString());
        int httpCode = novoVeiculoResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
    
    // Atualiza dado veiculo. Retorna true se bem sucedido e false para falha
    public static boolean atualizaVeiculo(int idVeiculo, VeiculoDTO veiculo) throws Exception{
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String request = gson.toJson(veiculo);
        
        //System.out.print(request);
        
        HttpRequest atualizaVeiculo = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/veiculo/" + idVeiculo))
                .PUT(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> atualizaVeiculoResposta = httpClient.send(atualizaVeiculo, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = atualizaVeiculoResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
    
    // Soft delete veiculo. Retorna true se bem sucedido e false para falha
    public static boolean desativarVeiculo(int idVeiculo) throws Exception{
        
        HttpRequest desativaVeiculo = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/veiculo/" + idVeiculo))
                .DELETE()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> desativaVeiculoResposta = httpClient.send(desativaVeiculo, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = desativaVeiculoResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
}
