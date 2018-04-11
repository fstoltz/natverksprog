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
public class Response implements Serializable{
    public boolean foundFriend;
    public String msg;
    public Kompis k;
    
    Response(String msg){
        this.foundFriend = false;
        this.msg = msg;
    }
}
