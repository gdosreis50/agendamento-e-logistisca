package service;

import Dto.MotoristaDTO;
import entidades.Motorista;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Date;





public class MotoristaService {
    
    public static String BASE_URL = "http://localhost/api/motorista";

    
    //retorna uma lista do tipo motorista com todos os motoristas ativos no BD
    public static List<Motorista> listarMotorista() throws Exception{

        HttpRequest getListMotorista = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/motorista"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListMotorista, BodyHandlers.ofString());
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Motorista>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo Motorista a partir de seu ID
    public static Motorista getMotorista(int id) throws Exception{
        
            HttpRequest getListMotorista = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/motorista/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListMotorista, BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type motorista = new TypeToken<Motorista>(){}.getType();
        
        return gson.fromJson(getResposta.body(), motorista);
    }
    
    
    // Cria novo motorista no BD e retorna true se der certo ou false para falha
    public static boolean novoMotorista(String nome, String cpf, String cnh, Date dataVenc, String catCnh, String telefone, int idFunc) throws Exception{
        
        
        MotoristaDTO motorista = new MotoristaDTO();
        
        motorista.setNomeMotorista(nome);
        motorista.setCpf(cpf);
        motorista.setCnh(cnh);
        motorista.setDataVencimentoCnh(dataVenc);
        motorista.setCategoriaCnh(catCnh);
        motorista.setTelefone(telefone);
        motorista.setIdfuncionario(idFunc);
        
        Gson gson = new Gson();
        String request = gson.toJson(motorista);
        
        //System.out.println(request);
        
        
        HttpRequest novoMotorista;
        novoMotorista = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novoMotResposta = httpClient.send(novoMotorista, BodyHandlers.ofString());
        int httpCode = novoMotResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
    
    // Atualiza dado motorista. Retorna true se bem sucedido e false para falha
    public static boolean atualizaMotorista(int idMotorista, String nome, String cpf, String cnh, Date dataVenc, String catCnh, String telefone, int idFunc) throws Exception{
        
        MotoristaDTO motorista = new MotoristaDTO();
        
        motorista.setNomeMotorista(nome);
        motorista.setCpf(cpf);
        motorista.setCnh(cnh);
        motorista.setDataVencimentoCnh(dataVenc);
        motorista.setCategoriaCnh(catCnh);
        motorista.setTelefone(telefone);
        motorista.setIdfuncionario(idFunc);
        
        Gson gson = new Gson();
        String request = gson.toJson(motorista);
        
        //System.out.print(request);
        
        HttpRequest atualizaMotorista = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/motorista/" + idMotorista))
                .PUT(BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> atualizaMotResposta = httpClient.send(atualizaMotorista, BodyHandlers.ofString());
        
        int httpCode = atualizaMotResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
    
    //Soft delete motorista. Retorna True se bem sucedido e false para falha
    public static boolean desativarMotorista(int idMotorista) throws Exception{
        
        HttpRequest desativaMotorista = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/motorista/" + idMotorista))
                .DELETE()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> desativaMotResposta = httpClient.send(desativaMotorista, BodyHandlers.ofString());
        
        int httpCode = desativaMotResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
}
