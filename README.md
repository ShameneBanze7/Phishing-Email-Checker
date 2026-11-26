# Phishing Email Checker

## What is this?
Have you ever received an email that just felt... off? Maybe it was a "bank" asking for your password, or a message saying you won a prize you never entered for. 

This project is like a cybersecurity-savvy friend who reads your emails before you do. You paste in an email, and the app tells you if it's safe, suspicious, or a phishing scam—and explains exactly *why*. 

It works in two layers:
1. **The Rule Checker:** Scans for obvious red flags using plain Java code (like urgent language, shady links, or requests for passwords).
2. **The AI Check (LLM):** Analyzes the context, tone, and manipulation tactics that simple code might miss.

## The Red Flags (Rule Layer)
The application awards points for each red flag it finds. If the score gets high enough, the email is flagged.

Here are the rules I built:
*   **Urgent Language (+3 points):** Words like "Urgent," "Immediately," or "Account Suspended."
*   **Sensitive Info Request (+4 points):** Asking for passwords, PINs, credit cards, or ID numbers.
*   **Generic Greeting (+1 point):** Starting with "Dear Customer" instead of your actual name.
*   **Insecure Links (+2 points):** Using `http://` instead of secure `https://`.
*   **Hidden Links (+3 points):** Using URL shorteners like `bit.ly` to hide the real destination.

**The Verdict:**
*   **0 to 2 points:** Safe
*   **3 to 5 points:** Suspicious
*   **6+ points:** Phishing

## How the Two Layers Work Together
This checker uses a dual-layer approach:
1. **Rule Checker:** Acts as a fast, deterministic filter checking for specific red flags (urgent language, sensitive info requests, bad links).
2. **LLM Check:** Acts as the context-aware brain, analyzing the tone and manipulation tactics. 
If either layer flags the email, the final combined verdict is escalated to Suspicious or Phishing.

## How to run it
1. Clone the repo.
2. Ensure you have the `samples` folder in the root of your project.
3. Run the `Main.java` file. 
4. The app will read the sample email from `samples/phishing1.txt`, analyze it through both layers, and print the final combined report to the console.

## Test Results
Here is how the detector performed against the sample phishing email:

| Scenario | Expected Result | Actual Result | Correct? |
| :--- | :--- | :--- | :--- |
| Sample Phishing Email (Urgent language, fake link, password request) | Phishing | Phishing (Rules Score: 13, AI: Phishing) | ✅ |

*Summary: The combined system correctly classified the phishing email with no false alarms.*

## Limitations (Being Honest)
*   **Rules can be bypassed:** A very well-written phishing email with perfect grammar and no links might slip past the rule checker.
*   **AI limitations:** In this build, the AI layer is simulated to ensure the application runs reliably during demonstrations without depending on live API keys or network uptime. 
*   **Context blindness:** The app doesn't know if you actually requested a password reset.
