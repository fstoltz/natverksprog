/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerMultiThreaded;

/**
 *
 * @author fstoltz
 */
public class Protocol {
 
    
    public Object parseInput(String clientString){
        return "I'm the server.. here's what I received: " + clientString;
    }
}
