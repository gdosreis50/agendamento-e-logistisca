package service;

import Dto.FuncionarioDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entidades.Funcionario;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class FuncionarioService {
        public static String BASE_URL = "http://localhost/api/funcionario/";

    
    //retorna uma lista do tipo Funcionario com todos os funcionarios ativos no BD
    public static List<Funcionario> listarFuncionarios() throws Exception{

        HttpRequest getListFuncionario = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/funcionario"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListFuncionario, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Funcionario>>(){}.getType();
        
        
        //System.out.println(getResposta.body());
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo Funcionario a partir de seu ID
    public static Funcionario getFuncionario(int id) throws Exception{
        
            HttpRequest getFuncionario = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/funcionario/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getFuncionario, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type funcionario = new TypeToken<Funcionario>(){}.getType();
        
        return gson.fromJson(getResposta.body(), funcionario);
    }
    
    
    // Cria novo Funcionario no BD e retorna true se der certo ou false para falha
    public static boolean novoFuncionario(String nome, String cpf) throws Exception{
        
        
        FuncionarioDTO funcionario = new FuncionarioDTO();
        
        funcionario.setNomeFunc(nome);
        funcionario.setCpf(cpf);
        
        
        Gson gson = new Gson();
        String request = gson.toJson(funcionario);
        
        //System.out.println(request);
        
        
        HttpRequest novoFuncionario;
        novoFuncionario = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novoFuncionarioResposta = httpClient.send(novoFuncionario, HttpResponse.BodyHandlers.ofString());
        int httpCode = novoFuncionarioResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
    
    // Atualiza dado funcionario. Retorna true se bem sucedido e false para falha
    public static boolean atualizaFuncionario(int idFuncionario, String nome, String cpf) throws Exception{
        
        FuncionarioDTO funcionario = new FuncionarioDTO();
        
        funcionario.setNomeFunc(nome);
        funcionario.setCpf(cpf);
        
        Gson gson = new Gson();
        String request = gson.toJson(funcionario);
        
        //System.out.print(request);
        
        HttpRequest atualizaFuncionario = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/funcionario/" + idFuncionario))
                .PUT(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> atualizaFuncionarioResposta = httpClient.send(atualizaFuncionario, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = atualizaFuncionarioResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
    
    //Soft delete funcionario. Retorna True se bem sucedido e false para falha
    public static boolean desativarFuncionario(int idFuncionario) throws Exception{
        
        HttpRequest desativaFuncionario = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/funcionario/" + idFuncionario))
                .DELETE()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> desativaFuncionarioResposta = httpClient.send(desativaFuncionario, HttpResponse.BodyHandlers.ofString());
        
        int httpCode = desativaFuncionarioResposta.statusCode();
        
        if(httpCode == 200){
            return true;
        }else{
            return false;
        }
    }
}
