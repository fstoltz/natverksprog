/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uppg9ClassChat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author fstoltz
 */
public class MulticastHandler implements Runnable{
    boolean printedConnectedOrNot = false;
    boolean printedMyPrivateIP = false;
    JTextArea area;
    
    MulticastHandler(JTextArea area) throws IOException{
        this.area = area;
    }

    
    @Override
    public void run(){
                
        try {
            
            InetAddress ownIP = InetAddress.getLocalHost();
            String myIP = ownIP.getHostAddress().toString();
            if(this.printedMyPrivateIP == false){
                this.area.append(">>> Your private IP is: " + myIP + "\n");
                this.printedMyPrivateIP = true; 
            }
            
            
            InetAddress grp = InetAddress.getByName("234.235.236.237");
            MulticastSocket recSocket = new MulticastSocket(12540);
            recSocket.joinGroup(grp);
            
            byte[] buffer = new byte[256];
            DatagramPacket myPkt = new DatagramPacket(buffer, buffer.length); //Create the packet
            System.out.println("I'm heeeere");
            
            if(this.printedConnectedOrNot == false){this.area.append(">>> Connected!\n");}
            this.printedConnectedOrNot = true;
            //this.textHistory += ">>> Connected!\n";
            //this.chatArea.setText(this.textHistory);
            while(true){ //THE THREADS LOCKS AT THIS WHILE LOOP, DOES IT FOREVER, .RECEIVE() is blocking, waits for a packet to arrive, then prints it, then starts waiting again
                //and the main thread is handling the ActionEvents/Listeners
                //this.connected = true;
                recSocket.receive(myPkt); //Receive the packet
                //this.connected = false;
                String received = new String(myPkt.getData(), 0, myPkt.getLength()); //the actual user payload
                LocalTime time = LocalTime.now();
                //this.textHistory += time.getHour() + ":" + time.getMinute() + " " + received + "\t\t\t(from: " + myPkt.getSocketAddress() + ")\n";
                
                this.area.setCaretPosition(area.getDocument().getLength());
                this.area.append(time.getHour() + ":" + time.getMinute() + " " + received + "\t\t\t\t(from: " + myPkt.getSocketAddress() + ")\n");
                //chatArea.setText(this.textHistory);
            }
            //System.out.println("RECIVED: " + received);
        } catch (Exception e){
            e.printStackTrace();
        }
          this.printedConnectedOrNot = false;
//        this.textHistory += msg + "\n";
//        chatArea.setText(this.textHistory);
    }
    
}
