/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerWithProtocol;

import java.util.*;


/**
 *
 * @author fstoltz
 */
public class KompisDAO {
    public List<Kompis> list = new ArrayList<>();
    
    
    public void addFriend(String name, String phone){
        Kompis newFriend = new Kompis(name, phone);
        list.add(newFriend);
    }
    
    
    public Kompis getFriendAsObj(String name){
        Kompis k = null;
        for (Kompis kompis : this.list){
            if(name.equals(kompis.name)){
                k = kompis;
                break;
            }
        }
        return k;
    }
    
    
    public String getFriendInfo(String name){
        String result = null;
        for(Kompis kompis : this.list){
            if(name.equals(kompis.name)){ //Found the friend, client string matches with database entry
                result = kompis.phone;
                break; //Break because friend has been found, no need to iterate further.
            }
        }
        if(result == null){ // couldn't find a friend with that name
            return "Could not find a friend with that name.";
        } else {
            return result;
        }
        
    }
}
