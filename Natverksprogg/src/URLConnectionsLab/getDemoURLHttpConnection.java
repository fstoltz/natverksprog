/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package URLConnectionsLab;

import java.net.*;
import java.io.*;
import java.util.*;




public class getDemoURLHttpConnection {
    
    
    
    public static void main(String[] args) throws MalformedURLException, IOException{
        URL myUrl = new URL("http://localhost:8080/RESTService/rest/ClimateService/viewall");
        HttpURLConnection conn;
        conn = (HttpURLConnection) myUrl.openConnection();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        
        String in;
        while((in = br.readLine()) != null){
            System.out.println(in);
        }
        br.close();
    }
}
