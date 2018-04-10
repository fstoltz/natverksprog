/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicTCPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author fstoltz
 */
public class Client {
    Socket tcpSocket;
    PrintWriter out;
    BufferedReader in;
    Scanner sc;
    
    Client() throws UnknownHostException, IOException{
        tcpSocket = new Socket(InetAddress.getLocalHost(), 15000);
        sc = new Scanner(System.in);
        
        this.out = new PrintWriter(tcpSocket.getOutputStream(), true);
        
        this.in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        
        String serverStr;
        while((serverStr = this.in.readLine()) != null){
            System.out.println("MESSAGE FROM SERVER: " + serverStr);
            this.out.println(sc.next());
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        Client client = new Client();
    }
    
}
