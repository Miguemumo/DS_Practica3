
package es.ugr.cliente;

import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;


public class ClienteREST {
    
    public static final String MI_URL = "http://localhost:8080/TomCat_DS/demo/hello/";
    
     public static String askServerFor(String path, String nombre){
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget webTarget = client.target(MI_URL).path(path);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        
        Response response = invocationBuilder.post(Entity.entity(nombre, MediaType.TEXT_PLAIN));
        String a = response.readEntity(String.class);

        return a;
    }
    
    public static void main(String[] args) {


    }

}
