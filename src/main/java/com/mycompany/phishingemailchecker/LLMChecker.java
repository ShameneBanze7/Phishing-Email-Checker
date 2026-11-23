/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.phishingemailchecker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import org.json.JSONObject;

/**
 * The LLMChecker class uses the Gemini AI model to analyze the context and tone of the email.
 * It connects to the Google Gemini API via HTTP.
 */
public class LLMChecker {

    // PASTE YOUR *NEW* API KEY HERE. DO NOT SHARE IT.
    private static final String API_KEY = "PASTE_YOUR_NEW_KEY_HERE"; 
    
    // The endpoint URL for the Gemini 1.5 Flash model
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public String analyzeWithAI(String emailText) {
        System.out.println("\n--- Contacting LLM (AI) for Context Analysis... ---");

        try {
            // 1. Create the JSON payload that asks the AI to analyze the email
            String safeEmailText = emailText.replace("\"", "\\\"").replace("\n", "\\n");
            
            String requestBody = """
                {
                  "contents": [{
                    "parts":[{"text": "You are a cybersecurity expert. Analyze this email for phishing signs. Reply with a short verdict (Safe, Suspicious, or Phishing) and a brief reason. Email: %s"}]
                  }]
                }
                """.formatted(safeEmailText);

            // 2. Build the HTTP Request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 3. Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. Parse the JSON response from Google
            JSONObject jsonResponse = new JSONObject(response.body());
            
            // Navigate through the JSON structure to get just the text response
            String aiText = jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            return aiText.trim();

        } catch (IOException | InterruptedException e) {
            return "AI Error: Could not connect to the LLM. " + e.getMessage();
        } catch (Exception e) {
            return "AI Error: Failed to parse the response. Check if your API key is correct.";
        }
    }
}