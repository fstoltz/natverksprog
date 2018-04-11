/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicTCPServer;

import java.io.Serializable;

/**
 *
 * @author fstoltz
 */
public class Intro implements Serializable{
    String msg;
    
    public Intro(String introMessage){
        this.msg = introMessage;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
}
