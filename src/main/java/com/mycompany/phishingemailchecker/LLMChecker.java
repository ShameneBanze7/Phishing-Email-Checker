/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.phishingemailchecker;

/**
 * The LLMChecker class acts as the AI layer.
 * For the purpose of this practical exam, this class simulates the AI's response 
 * to ensure the project runs reliably without depending on external network connections.
 */
public class LLMChecker {

    public String analyzeWithAI(String emailText) {
        System.out.println("\n--- Contacting LLM (AI) for Context Analysis... ---");
        
        // Simulate a brief network delay
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        String lowerText = emailText.toLowerCase();

        if (lowerText.contains("urgent") && lowerText.contains("password")) {
            return "Verdict: Phishing. Reason: The email uses high-pressure language ('URGENT!') and asks for sensitive information under the guise of account verification. This is a classic social engineering tactic.";
        } 
        else if (lowerText.contains("http://") || lowerText.contains("bit.ly")) {
            return "Verdict: Suspicious. Reason: The email contains insecure or hidden links that could lead to malicious sites.";
        } 
        else {
            return "Verdict: Safe. Reason: No obvious manipulation tactics or sensitive requests were detected.";
        }
    }
}