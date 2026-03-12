package service;

import Dto.ChecklistDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entidades.CheckList;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;

public class ChecklistService {
    public static String BASE_URL = "http://localhost/api/checklist/";

    
    //retorna uma lista do tipo CheckList com todas os checklists ativos no BD
    public static List<CheckList> listarChecklists() throws Exception{

        HttpRequest getListCheck = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/checklist"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListCheck, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CheckList>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo CheckList a partir de seu ID
    public static CheckList getChecklist(int id) throws Exception{
        
            HttpRequest getCheck = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/checklist/" + id))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getCheck, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type check = new TypeToken<CheckList>(){}.getType();
        
        return gson.fromJson(getResposta.body(), check);
    }
    
    
    // Cria novo checklist no BD e retorna true se der certo ou false para falha
    public static boolean novoChecklist(ChecklistDTO check) throws Exception{
        
        
        Gson gson = new Gson();
        String request = gson.toJson(check);
        
        //System.out.println(request);
        
        
        HttpRequest novoCheck;
        novoCheck = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> novoCheckResposta = httpClient.send(novoCheck, HttpResponse.BodyHandlers.ofString());
        int httpCode = novoCheckResposta.statusCode();
        
        if(httpCode == 201){
            return true;
        } else {
            return false;
        }
    }
}
