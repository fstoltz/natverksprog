/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerWithProtocolSINGLETHREADED;

import java.io.Serializable;

/**
 *
 * @author fstoltz
 */
public class Kompis implements Serializable {
    public String name;
    public String phone;
    
    public Kompis(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
