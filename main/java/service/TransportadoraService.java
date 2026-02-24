package service;

import Dto.TransportadoraDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entidades.Transportadora;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TransportadoraService {
    public static String BASE_URL = "http://localhost/api/transportadora/";

    
    //retorna uma lista do tipo transportadora com todas as transportadoras ativas no BD
    public static List<Transportadora> listarTransportadora() throws Exception{

        HttpRequest getListTransportadora = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/transportadora"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListTransportadora, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Transportadora>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo transportadora a partir de seu ID
    public static Transportadora getTransportadora(int id) throws Exception{
        
            HttpRequest getTransportadora = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/transportadora/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getTransportadora, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type transportadora = new TypeToken<Transportadora>(){}.getType();
        
        return gson.fromJson(getResposta.body(), transportadora);
    }
    
    
    // Cria nova transportadora no BD e retorna true se der certo ou false para falha
    public static boolean novaTransportadora(String nomeTransportadora, String cnpj, String antt, String email, int idFunc) throws Exception{
        
        
        TransportadoraDTO transportadora = new TransportadoraDTO();
        
        
        transportadora.setNomeTransportadora(nomeTransportadora);
        transportadora.setCnpj(cnpj);
        transportadora.setAntt(antt);
        transportadora.setEmail(email);
        transportadora.setIdfuncionario(idFunc);
        
        Gson gson = new Gson();
        String request = gson.toJson(transportadora);
        
        //System.out.println(request);
        
        
        HttpRequest novaTransportadora;
        novaTransportadora = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novaTransportadoraResposta = httpClient.send(novaTransportadora, HttpResponse.BodyHandlers.ofString());
        int httpCode = novaTransportadoraResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
    
    // Atualiza dada transportadora. Retorna true se bem sucedido e false para falha
    public static boolean atualizaTransportadora(int idTransportadora, String nomeTransportadora, String cnpj, String antt, String email, int idFunc) throws Exception{
        
        TransportadoraDTO transportadora = new TransportadoraDTO();
        
        
        transportadora.setNomeTransportadora(nomeTransportadora);
        transportadora.setCnpj(cnpj);
        transportadora.setAntt(antt);
        transportadora.setEmail(email);
        transportadora.setIdfuncionario(idFunc);;
        
        Gson gson = new Gson();
        String request = gson.toJson(transportadora);
        
        //System.out.print(request);
        
        HttpRequest atualizaTransportadora = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/transportadora/" + idTransportadora))
                .PUT(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> atualizaTransportadoraResposta = httpClient.send(atualizaTransportadora, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = atualizaTransportadoraResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
    
    //Soft delete transportadora. Retorna True se bem sucedido e false para falha
    public static boolean desativarTransportadora(int idTransportadora) throws Exception{
        
        HttpRequest desativaTransportadora = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/transportadora/" + idTransportadora))
                .DELETE()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> desativaTransportadoraResposta = httpClient.send(desativaTransportadora, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = desativaTransportadoraResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
}
