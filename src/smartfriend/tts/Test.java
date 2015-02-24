/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.tts;

import java.util.Scanner;

/**
 *
 * @author Meuru
 */
public class Test {
    
    
    public static void main(String[] args) {
        System.out.println("@@@@");
        VoiceGenerator.getVoiceGeneratorInstance().voiceOutput("I'm talking");
        while (true) { 
             Scanner scanner = new Scanner(System.in);
    VoiceGenerator.getVoiceGeneratorInstance().voiceOutput(scanner.nextLine());
        }
    }
}
