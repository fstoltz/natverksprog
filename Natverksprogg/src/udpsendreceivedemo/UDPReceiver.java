/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpsendreceivedemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author fstoltz
 */
public class UDPReceiver {
    public static void main(String[] args) throws SocketException, IOException{
        DatagramSocket udpSocket;
        udpSocket = new DatagramSocket(35000);
        
        byte[] data = new byte[512];
        
        while(true){
            DatagramPacket packet;
            packet = new DatagramPacket(data, data.length);
            udpSocket.receive(packet);
            
            String msg = new String(packet.getData());
            System.out.println(msg);
        }
        
    }
}
