/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerWithProtocolSINGLETHREADED;

/**
 *
 * @author fstoltz
 */
public class Protocol {
    private static final int INITIAL_MSG = 0;
    private static final int WAITING_FOR_REQUEST = 1;
    
    private int state = INITIAL_MSG;
    
    KompisDAO dao = new KompisDAO();
    
    public Protocol(){
        /*Creates some kompisar and adds them to the dao object*/
        Kompis k1 = new Kompis("Olof", "0707754607");Kompis k2 = new Kompis("Peter", "0213107");Kompis k3 = new Kompis("Kalle", "0107131");Kompis k4 = new Kompis("Erik", "001931");Kompis k5 = new Kompis("Pachi", "764183");dao.addFriend("Olof", "0707754607");dao.addFriend("Kalle", "74871471");dao.addFriend("Pachi", "3651743");dao.addFriend("Mera", "837189351");
    }

    
    public Object parseInput(String clientString){
        Object output = null;
        
        String dbResponse;
        
        if(state == INITIAL_MSG){
            //Return something that prompts the user for a friend name (which will cause the server to receive that name and try to find it in the datbase)
            output = "Enter the name of a friend: ";
            state = WAITING_FOR_REQUEST;
        }
        else if(state == WAITING_FOR_REQUEST) {
            output = dao.getFriendInfo(clientString);
        }
    
    return output; //this gets sent to the client
    }
    
}
