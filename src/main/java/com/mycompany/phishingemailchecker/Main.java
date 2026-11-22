/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.phishingemailchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The main entry point for the Phishing Email Checker.
 * This class reads a sample email from a file, passes it to the RuleChecker, 
 * and prints the final security report to the console.
 */
public class Main {
    public static void main(String[] args) {
        
        // WHY THIS CHANGED:
        // Maven runs the app from a hidden 'target' folder, which breaks standard relative paths.
        // System.getProperty("user.dir") forces Java to find the absolute root folder of the project.
        String projectRoot = System.getProperty("user.dir");
        String filePath = projectRoot + "/samples/phishing1.txt";
        
        try {
            // Read all bytes from the text file and convert them into a String
            String emailText = new String(Files.readAllBytes(Paths.get(filePath)));
            
            System.out.println("--- Analyzing Email: " + filePath + " ---\n");
            
            // Create a new RuleChecker instance and pass the email text to the analyze method
            RuleChecker checker = new RuleChecker();
            checker.analyze(emailText);

            // Print out the final results
            System.out.println("VERDICT: " + checker.getVerdict());
            System.out.println("RISK SCORE: " + checker.getRiskScore() + " points");
            System.out.println("REASONS:");
            
            // If the list of reasons is empty, it means no red flags were found
            if (checker.getDetectedReasons().isEmpty()) {
                System.out.println("- No red flags found.");
            } else {
                // Otherwise, loop through the list and print each reason
                for (String reason : checker.getDetectedReasons()) {
                    System.out.println("- " + reason);
                }
            }

        } catch (IOException e) {
            // Catch errors if the file is missing or cannot be read
            System.out.println("Error reading file: " + filePath);
            System.out.println("Make sure you created the 'samples' folder in your project root and the .txt file!");
        }
    }
}