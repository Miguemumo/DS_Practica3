package es.ugr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Path("hello")
public class Servicio {
    
    ConfigurationBuilder cb = new ConfigurationBuilder()
        .setDebugEnabled(true)
        .setOAuthConsumerKey("YP6OXU6VdRkTWHqqiJk7z73hS")
        .setOAuthConsumerSecret("XWzcuba7jEOP6cK0ZR60Ib7Yj1BvL1Anro85EXH6gaWXbfDJr0")
        .setOAuthAccessToken("855025927-HDRhUNJfU3tgyAfRjDL00XDedwT35PZQQFlLsfrs")
        .setOAuthAccessTokenSecret("Vnv4oxliq3VcNAXuK4fucR6KkXllt5sMVke31tLFOTvrI");
        
    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();


    @POST
    @Path("/twitterFollowers")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String TwitterFollowers(String username) throws TwitterException, IOException {
        
        User user = twitter.showUser(username); 
        
        int followerCount = user.getFollowersCount();
        
        return Integer.toString(followerCount);        
    }
    
    @POST
    @Path("/twitterFollowed")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String TwitterFollowed(String username) throws TwitterException, IOException {
        
        User user = twitter.showUser(username); 
        
        int followedCount = user.getFriendsCount();
        
        return Integer.toString(followedCount);  
               
    }
    
    @POST
    @Path("/twitterPrivacy")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String TwitterPrivacy(String username) throws TwitterException, IOException {
        
        User user = twitter.showUser(username); 
        
        Boolean privacy = user.isProtected();
        
        String a_devolver = "";
        if (privacy){
            a_devolver = "Privado";
        }else{
            a_devolver = "Publico";
        }
        
        return a_devolver;        
    }
    
    @POST
    @Path("/twitterBiography")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String TwitterBiography(String username) throws TwitterException, IOException {
        
        User user = twitter.showUser(username); 

        return user.getDescription();  
    }
    
    @POST
    @Path("/twitterVerified")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String TwitterVerified(String username) throws TwitterException, IOException {
        
        User user = twitter.showUser(username); 
        
        Boolean verified = user.isVerified();
        
        String a_devolver = "";
        if (verified){
            a_devolver = "Verificado";
        }else{
            a_devolver = "No verificado";
        }

        return a_devolver;  
    }
    
    @POST
    @Path("/instagramFollowers")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String InstagramFollowers(String username) throws IOException {
        
        return getInstagramInfo(username, "edge_followed_by");
               
    }
    
    @POST
    @Path("/instagramFollowed")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String InstagramFollowed(String username) throws IOException {
        
        return getInstagramInfo(username, "edge_follow");
        
    }
    
    @POST
    @Path("/instagramPrivacy")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String InstagramPrivacy(String username) throws IOException {
        
        String a_devolver = "";
        if (getInstagramInfo(username, "is_private").equals("true")){
            a_devolver = "Privado";
        }else{
            a_devolver = "Publico";
        }
        
        return a_devolver;
    }
    
    @POST
    @Path("/instagramBiography")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String InstagramBiography(String username) throws IOException {
        
        return getInstagramInfo(username, "graphql");
    }
    
    @POST
    @Path("/instagramVerified")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String InstagramVerified(String username) throws IOException {
        
        String a_devolver = "";
        if (getInstagramInfo(username, "is_verified").equals("true")){
            a_devolver = "Verificado";
        }else{
            a_devolver = "No verificado";
        }
        
        return a_devolver;
    }
    
    
    public String getInstagramInfo(String username, String value) throws IOException{
        BufferedReader reader = null;
        String a_devolver = "";
        try {
            URL url = new URL("https://www.instagram.com/"+username+"/?__a=1");
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1){
                buffer.append(chars, 0, read); 
            }
            String texto = buffer.toString();
            
            String[] textoSeparado = texto.split(",");
            
            
            
            for (String a : textoSeparado){
                a = a.replace("{", "");
                a = a.replace("}", "");
                a = a.replace("\"", "");
                String[] campo = a.split(":");

                if (campo[0].equals(value)){
                    a_devolver = campo[campo.length-1];
                }
            }
        } finally {
            if (reader != null)
                reader.close();
        }
        
        return a_devolver;
    }


}
