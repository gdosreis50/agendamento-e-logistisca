package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entidades.Pedido;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Pedido>>(){}.getType();
        
        
        //String resposta = getResposta.body();
        
        return gson.fromJson(getResposta.body(), listType);
    }
    
    //Retorna um objeto tipo Pedido a partir de seu ID
    public static Pedido getPedido(int numPedido) throws Exception{
        
            HttpRequest getPedido = HttpRequest.newBuilder()
                .uri(new URI("http://localhost/api/pedido/" + numPedido))
                .GET()
                .build();
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResposta = httpClient.send(getPedido, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        
        Type pedido = new TypeToken<Pedido>(){}.getType();
        
        return gson.fromJson(getResposta.body(), pedido);
    }
}
