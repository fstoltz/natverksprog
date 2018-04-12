/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OracleKnockKnockGÅTA;


import java.net.*;
import java.io.*;

public class KnockKnockProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private String[] clues = { "What becomes bigger and bigger, they more take away?", "What is 5+5?", "Who is above you?", "What is your name?", "Who is sitting next to you?" };
    private String[] answers = { "A hole!",
                                 "10",
                                 "God",
                                 "Fredrik",
                                 "Anton" };

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            theOutput = "Do you want to receive a riddle? (y/n)";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = clues[currentJoke];
                state = SENTCLUE;
            } else { //exit if user entered no
                if(theInput.equalsIgnoreCase(answers[currentJoke])){
                    theOutput = "Correct!";
                } else {
                    
                }
                theOutput = "Wrong ansss";
                state = SENTCLUE;
                //System.exit(0);
            }
        } else if (state == SENTCLUE) {
            if (theInput.equalsIgnoreCase(answers[currentJoke])) {
                theOutput = answers[currentJoke] + ", that's right! Want another? (y/n)";
                state = ANOTHER;
            } else {
                /*theOutput = "You're supposed to say \"" + 
			    clues[currentJoke] + 
			    " who?\"" + */
                theOutput = "Wrong answer!";
                state = SENTKNOCKKNOCK;
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                //theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                theOutput = clues[currentJoke];
                state = SENTKNOCKKNOCK;
            } else {
                theOutput = "Bye.";
                state = WAITING;
            }
        }
        return theOutput;
    }
}