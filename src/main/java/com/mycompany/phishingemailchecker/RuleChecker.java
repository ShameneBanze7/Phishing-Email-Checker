/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.phishingemailchecker;

// Import ArrayList and List to store the reasons why an email is flagged
import java.util.ArrayList;
import java.util.List;

/**
 * The RuleChecker class analyzes email text for specific red flags.
 * I separated each rule into its own method to make the code easy to test, 
 * maintain, and explain. If a rule needs adjusting, I only have to change it in one place.
 */
public class RuleChecker {

    // These variables store the results of the analysis
    private List<String> detectedReasons = new ArrayList<>();
    private int riskScore = 0;

    /**
     * This is the main method that runs all the checks.
     * It takes the raw email text as input.
     * 
     * @param emailText The body of the email to be analyzed.
     */
    public void analyze(String emailText) {
        // I clear these first so that if this object is reused, it starts fresh
        detectedReasons.clear();
        riskScore = 0;

        // I call each rule method one by one. 
        // Keeping them separate makes debugging much easier.
        checkUrgentLanguage(emailText);
        checkSensitiveInfo(emailText);
        checkGenericGreeting(emailText);
        checkLinks(emailText);
    }

    /**
     * Rule 1: Checks for words that create a false sense of urgency.
     * Phishers use this to make victims panic and act without thinking.
     */
    private void checkUrgentLanguage(String text) {
        // I convert the text to lowercase so the check is case-insensitive 
        // (so "URGENT" and "urgent" both match)
        String lowerText = text.toLowerCase();
        String[] urgentKeywords = {"urgent", "immediately", "account suspended", "24 hours", "act now", "final warning"};
        
        for (String keyword : urgentKeywords) {
            if (lowerText.contains(keyword)) {
                riskScore += 3; // Urgent language adds 3 points to the risk score
                detectedReasons.add("Urgent/threatening language detected ('" + keyword + "').");
                break; // I break out of the loop after finding one match so I don't add 3 points multiple times for the same category
            }
        }
    }

    /**
     * Rule 2: Checks if the email asks for sensitive information.
     * Legitimate companies never ask for passwords or PINs via email.
     */
    private void checkSensitiveInfo(String text) {
        String lowerText = text.toLowerCase();
        String[] sensitiveKeywords = {"password", "pin", "credit card", "id number", "ssn", "verify your account"};
        
        for (String keyword : sensitiveKeywords) {
            if (lowerText.contains(keyword)) {
                riskScore += 4; // Asking for sensitive info is a massive red flag, so it gets 4 points
                detectedReasons.add("Sensitive information request detected ('" + keyword + "').");
                break; 
            }
        }
    }

    /**
     * Rule 3: Checks for generic greetings.
     * Real companies usually use your name. Scammers use "Dear Customer" 
     * because they send the same email to thousands of people at once.
     */
    private void checkGenericGreeting(String text) {
        String lowerText = text.toLowerCase();
        if (lowerText.contains("dear customer") || lowerText.contains("dear user") || lowerText.contains("valued client")) {
            riskScore += 1; // Only 1 point because some legitimate automated emails use generic greetings too
            detectedReasons.add("Generic greeting detected (e.g., 'Dear Customer').");
        }
    }

    /**
     * Rule 4: Checks for suspicious link structures.
     * I look for insecure HTTP connections and URL shorteners which hide the real destination.
     */
    private void checkLinks(String text) {
        String lowerText = text.toLowerCase();
        
        // Insecure HTTP (missing the 's' in https) is a sign the site doesn't encrypt data
        if (lowerText.contains("http://")) {
            riskScore += 2;
            detectedReasons.add("Insecure HTTP link detected (missing 's' in https).");
        }
        
        // URL shorteners like bit.ly are often used to hide malicious links
        if (lowerText.contains("bit.ly") || lowerText.contains("tinyurl")) {
            riskScore += 3;
            detectedReasons.add("URL shortener detected. Hidden destination.");
        }
    }

    /**
     * This method calculates the final verdict based on the total risk score.
     * 
     * @return A String verdict: "PHISHING", "SUSPICIOUS", or "SAFE"
     */
    public String getVerdict() {
        if (riskScore >= 6) {
            return "PHISHING";
        } else if (riskScore >= 3) {
            return "SUSPICIOUS";
        } else {
            return "SAFE";
        }
    }

    // Getters so the Main class can read the private variables
    public int getRiskScore() { 
        return riskScore; 
    }
    
    public List<String> getDetectedReasons() { 
        return detectedReasons; 
    }
}