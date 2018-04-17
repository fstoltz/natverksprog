/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package URLConnectionsLab;

import java.net.*;
import java.io.*;
import java.util.*;




public class getDemoRaw {
    
    public static void main(String[] args) throws UnknownHostException, IOException{
        //skapa upp en socket till urlen
        Socket s;
        s = new Socket(InetAddress.getByName("huerty.com"), 80);
        
        //skapa upp en outputström till socketen
        PrintWriter out = new PrintWriter(s.getOutputStream());
        
        //skriv en HTTP request till socketen
        out.println("GET /content/umlSDC.png HTTP/1.1");
        out.println("Host: huerty.com");
        out.println("");
        out.flush();
        
        //skapa en inputström till socketen, läs ifrån denna & få responset ifrån requesten
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        String responseLine;
        
        while((responseLine = in.readLine()) != null){
            System.out.println(responseLine);
        }
        
    }
}
