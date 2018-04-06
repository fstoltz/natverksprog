/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uppgift4;

/**
 *
 * @author fstoltz
 */
public class Medicine extends Thread{
    String medType;
    int interval;
    
    Medicine(String med, int interval){
        this.medType = med;
        this.interval = interval;
    }
    
    @Override
    public void run(){
        System.out.println("Hello from a thread.");
    }
    
}
