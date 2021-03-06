/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpsendreceivedemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author fstoltz
 */
public class UDPSendReceiveDemo {

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        InetAddress toAdr;
        toAdr = InetAddress.getLocalHost();
        DatagramSocket udpSocket;
        udpSocket = new DatagramSocket();
        
        String msg;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while(true){
            String city = JOptionPane.showInputDialog("Enter name of city: ");
            msg = JOptionPane.showInputDialog("Enter temperature value: ");
            if(msg.equals("EXIT")){ break; }
            
            String completeMsg = city + "-" + msg;
            
            byte[] data = (completeMsg.getBytes());
            DatagramPacket packet;
            packet = new DatagramPacket(data, data.length, toAdr, 35000);
            udpSocket.send(packet);
        }
    }
}
