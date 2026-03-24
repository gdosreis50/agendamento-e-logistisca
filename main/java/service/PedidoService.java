package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.CheckList;
import entidades.Pedido;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.LocalDateAdapter;

public class PedidoService {
    public static String BASE_URL = "http://localhost/api/pedido/";

    
    //retorna uma lista do tipo Pedido com todas os pedidos ativos no BD
    public static List<Pedido> listarPedido() throws Exception{

        HttpRequest getListPedido = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/pedido"))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getListPedido, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        Type listType = new TypeToken<List<Pedido>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        List<Pedido> lista = new ArrayList<Pedido>();
        
        lista = gson.fromJson(getResposta.body(), listType);
        
        if (lista == null){
            return Collections.EMPTY_LIST;
        }else{
            return lista;
        }
    }
    
    //Retorna um objeto tipo Pedido a partir de seu ID
    public static Pedido getPedido(String numPedido) throws Exception{
        
            HttpRequest getPedido = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/pedido/" + numPedido))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getPedido, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        
        Type pedido = new TypeToken<Pedido>(){}.getType();
        
        return gson.fromJson(getResposta.body(), pedido);
    }
}
