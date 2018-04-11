/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicTCPServer;

import BasicTCPClient.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
    
    Client() throws UnknownHostException, IOException, ClassNotFoundException{
        tcpSocket = new Socket(InetAddress.getLocalHost(), 15000);
        sc = new Scanner(System.in);
        
        this.out = new PrintWriter(tcpSocket.getOutputStream(), true);
        
        this.in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        
        //ObjectInputStream reader
        ObjectInputStream objIn;
        objIn = new ObjectInputStream(tcpSocket.getInputStream());
        /*THOUGHTS:
        Separation of concerns. Currently, the Client has access to the same libraries that the server
        is using for creating and manipulating objects. The client does the job of
        choosing what data to display etc.
        
        
        
        
        */
        Object unknown;
        while((unknown = objIn.readObject()) != null){
            if(unknown instanceof Intro){ //if its the intro message that has been sent
                System.out.println(((Intro) unknown).getMsg());
            }
            else if(unknown instanceof Kompis){ //this has now become useless because all responses from server are of type 'Response'
                System.out.println(((Kompis) unknown).getPhone());
            }
            else if(unknown instanceof Response) {
                if(((Response) unknown).foundFriend == true){
                    System.out.println(((Response) unknown).k.phone); //extracts(prints) the phone value of the friend that was serialized and sent by the server
                } else { //foundfriend is set to false and therefore we should not attempt to extract data from the k variable because it will
                        //generate a nullpointerexception because the k was initailized to null serverside. Only if KompisDAO find the friend does it
                        //associate k with the desired friend.
                    System.out.println(((Response) unknown).msg);
                }
                
            } else {
                System.out.println("Something bad happened.");
            }
            this.out.println(sc.nextLine()); //Takes input from the user and sends it as a string to the socket(the server)
        }
        
        
//        Object unknown = objIn.readObject();
//        if(unknown instanceof Intro){
//            //print intro data (string)
//            System.out.println(((Intro) unknown).getMsg());
//        }
//        else if(unknown instanceof Kompis){
//            //print kompis data
//        }
//        
//        this.out.println(sc.nextLine());
//        Kompis k;
//        while((k = (Kompis) objIn.readObject()) != null){
//            System.out.println("MESSAGE FROM SERVER: " + k.phone);
//        }
//        
//        String serverStr;
//        while((serverStr = this.in.readLine()) != null){
//            System.out.println("MESSAGE FROM SERVER: " + serverStr);
//            this.out.println(sc.next());
//        }
    }
    
    
    public static void main(String[] args) throws IOException, UnknownHostException, ClassNotFoundException{
        Client client = new Client();
    }
    
}
