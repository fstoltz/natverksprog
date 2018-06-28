/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inlam1.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author fstoltz
 */
public class RESTClientCLI {
    private boolean running; //Keeps program running until user chooses to exit it via the runMenu() option (11)
    Scanner sc = new Scanner(System.in); //Scanner used to retrieve user input via stdin
    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource service = client.resource("http://localhost:8080/RESTService");
    
    //private static WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8080/RESTService").build());
    
    public void printMenu(){
        System.out.println("\n--- MAIN MENU ---\n\n Options:\n"
                + "(1) View Panel\n(2) Set Temperature\n(3) Set Humidity\n"
                + "(4) Set Light\n(5) Energy used today\n"
                + "(6) Humidity\t - Weekly Report\n(7) Temperature\t - Weekly Report\n"
                + "(8) Energy\t - Weekly Report \n(9) Snapshot all parameters\n"
                + "(10) View Snapshots\n(11) Exit\n\nChoice: ");
    }
    
    public void enterToContinue() throws IOException{
        System.out.println("\nPress ENTER to return to menu...");
        try {
            System.in.read();
        } catch (InputMismatchException e){ //TODO, STILL EXITS PROGRAM IF USER ENTERS RANDOM KEYS AS INPUT
            //DO NOTHING, USER IS ALLOWED TO ENTER ANYTHING TO GET BACK TO THE MENU
        }
    }
    
    public void runMenu() throws IOException{
        System.out.println("\n\n> Attempting to connect to REST Service...");
        if (this.testConnection()){ // Tests whether the REST-Service is online or not
            System.out.println("\n> Connected!\n\n");
            this.running = true;
        } else {
            System.out.println("\n> REST-Service connection attempt failed, please contact REST-API provider.\n\n> Exiting...");
            this.running = false; // If we could not successfully connect to the REST-Service, exit program immediately.s
        }
        while(running){
            this.printMenu();
            int choice = sc.nextInt();
            System.out.println("You chose: " + choice + "\n");

            switch(choice){
                case 1:
                    System.out.println("---------------PANEL----------------------");
                    System.out.println(this.getRequest("viewall"));
                    System.out.println("------------------------------------------");
                    this.enterToContinue();
                    break;
                case 2:
                    System.out.println(this.setNewVal("temp"));
                    this.enterToContinue();
                    break;
                case 3:
                    System.out.println(this.setNewVal("hum"));
                    this.enterToContinue();
                    break;
                case 4:
                    System.out.println(this.setNewVal("light"));
                    this.enterToContinue();
                    break;
                case 5:
                    System.out.println(this.getRequest("energytoday"));
                    this.enterToContinue();
                    break;
                case 6:
                    System.out.println("--------------WEEKLY HUMIDITY REPORT------------------");
                    System.out.println(this.getRequest("humreport"));
                    System.out.println("------------------------------------------------------");
                    this.enterToContinue();
                    break;
                case 7:
                    System.out.println("--------------WEEKLY TEMPERATURE REPORT------------------");
                    System.out.println(this.getRequest("tempreport"));
                    System.out.println("---------------------------------------------------------");
                    this.enterToContinue();
                    break;
                case 8:
                    System.out.println("--------------WEEKLY ENERGY REPORT------------------");
                    System.out.println(this.getRequest("lightreport"));
                    System.out.println("----------------------------------------------------");
                    this.enterToContinue();
                    break;
                case 9:
                    System.out.println("Successfully created " + this.getRequest("snapshotnow") + " record of all parameters.");
                    this.enterToContinue();
                    break;
                case 10:
                    System.out.println(this.getSnapShots());
                    this.enterToContinue();
                    break;
                case 11:
                    System.out.println("Exiting...");
                    this.running = false;
                    break;
            }
        }
    }
    
    
    public boolean testConnection(){
        String info = "";
        try {
            info = service.path("")
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
        } catch(ClientHandlerException e){
            //DOES NOT HANDLE EXCEPTION, FAULT IS SERVER-SIDE
        }
        return info.equals("RUNNING");
    }
    /**
     * 
     * @param type String representing the final part of the URL
     * @return String containing formatted information, dependent on what value the parameter type was given.
     */

    public String getRequest(String type){
        String info = service.path("rest/ClimateService/"+type)
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
        return info;
    }

    /**
     * 
     * @return a String containing formatted information regarding snapshots
     */
    public String getSnapShots(){
        System.out.println("Enter limit for snapshot retrieval...");
        String info = service.path("rest/ClimateService/getsnapshots/limit/"+sc.nextInt())
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
        return info;
    }

    /**
     * 
     * @param type String defining what type of value to send to the REST-Service, also determines what message to prompt user input with
     * @return a formatted String containing the response from the REST-Service
     */
    public String setNewVal(String type){
        if (type.equals("light")){
            System.out.println("Enter dim-value for light: ");
        } else if (type.equals("temp")){
            System.out.println("Enter desired temperature: ");
        } else if (type.equals("hum")){
            System.out.println("Enter desired humidity: ");
        }
        String message = service.path("rest/ClimateService/log"+type+"now/"+sc.nextInt())
                .accept(MediaType.TEXT_PLAIN)
                .get(String.class);
        return message;
    }
    
    /**
     * 
     * @param args As of now, there's no switches.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException{
        RESTClientCLI cli = new RESTClientCLI();
        cli.runMenu();
    }
}
