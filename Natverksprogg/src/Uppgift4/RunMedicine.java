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
public class RunMedicine {
    public static void main(String[] args){
        Medicine newMed = new Medicine("Cola", 40);
        
        System.out.println(newMed.getState());
        
        newMed.start();
        
        System.out.println(newMed.getState());
    }
}
