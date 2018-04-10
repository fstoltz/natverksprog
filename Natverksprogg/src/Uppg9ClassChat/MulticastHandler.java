package Uppg9ClassChat;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JTextArea;


public class MulticastHandler implements Runnable{
    boolean printedConnectedOrNot = false;
    boolean printedMyPrivateIP = false;
    public boolean connected = false;
    
    JTextArea area;
    
    MulticastSocket socket = new MulticastSocket(12540);
    InetAddress grp = InetAddress.getByName("234.235.236.237");
    
    public void sendMessage(String msg) throws IOException{
        //ThreadLocalRandom.current().nextInt(30000, 40000);
        DatagramPacket myPkt = new DatagramPacket(msg.getBytes(), msg.length(), this.grp, 12540); //Create the packet
        socket.send(myPkt); //Main thread sends the packet
    }

    MulticastHandler(JTextArea area) throws IOException{ //The area parameter seems to hold a pointer to the original obj, not a copied TextARea, so this is Call by Reference
        this.area = area;
    }

    @Override
    public void run(){
        try {
            InetAddress ownIP = InetAddress.getLocalHost();
            String myIP = ownIP.getHostAddress().toString();
            if(this.printedMyPrivateIP == false){
                this.area.setForeground(Color.CYAN);
                this.area.append(">>> Your private IP is: " + myIP + "\n");
                this.printedMyPrivateIP = true; 
            }
            this.socket.joinGroup(this.grp);
            byte[] buffer = new byte[256];
            DatagramPacket myPkt = new DatagramPacket(buffer, buffer.length); //Create the packet
            //System.out.println("I'm heeeere");
            if(this.printedConnectedOrNot == false){
                //this.area.append(">>> Connected!\n");
                this.printedConnectedOrNot = true;
            }
            while(this.connected == true){ //THE THREADS LOCKS AT THIS WHILE LOOP, DOES IT FOREVER, .RECEIVE() is blocking, waits for a packet to arrive, then prints it, then starts waiting again
                //and the main thread is handling the ActionEvents/Listeners
                //this.connected = true;
                this.socket.receive(myPkt); //Receive the packet, the thread "locks" here, i.e waiting for a packet to arrive
                if(this.connected == true){ //Check if the user has pressed "Disconnect" whilst the thread has been locked at .receive()
                    //this.connected = false;
                    String received = new String(myPkt.getData(), 0, myPkt.getLength()); //the actual user payload
                    LocalTime time = LocalTime.now();
                    this.area.setCaretPosition(area.getDocument().getLength());
                    this.area.append(time.getHour() + ":" + time.getMinute() + " " + received + "\t\t\t\t(from: " + myPkt.getSocketAddress() + ")\n");
                } else { //User has pressed the 'disconnect' button and do not wish to view the last packet, throw it away
                    //this.area.setCaretPosition(area.getDocument().getLength());
                    //this.area.append(">>> Disconnected!\n");
                }
                
                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
          this.printedConnectedOrNot = false;
    }
}
