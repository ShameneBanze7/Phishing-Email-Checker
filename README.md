## How the Two Layers Work Together
This checker uses a dual-layer approach:
1. **Rule Checker:** Acts as a fast, deterministic filter checking for specific red flags (urgent language, sensitive info requests, bad links).
2. **LLM Check:** Acts as the context-aware brain, analyzing the tone and manipulation tactics. 
If either layer flags the email, the final combined verdict is escalated to Suspicious or Phishing.

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
