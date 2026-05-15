package fabriziopesaresi.CapstoneProject_GymPulse.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailgunService {

    @Value("${mailgun.api-key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.from}")
    private String from;

    private final OkHttpClient client = new OkHttpClient();

    public void sendEmail(String to, String subject, String text) {
        RequestBody formBody = new FormBody.Builder()
                .add("from", from)
                .add("to", to)
                .add("subject", subject)
                .add("text", text)
                .build();

        Request request = new Request.Builder()
                .url("https://api.mailgun.net/v3/" + domain + "/messages")
                .addHeader("Authorization", Credentials.basic("api", apiKey))
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("Mailgun error: " + response.body().string());
            }
        } catch (IOException e) {
            System.out.println("Mailgun exception: " + e.getMessage());
        }
    }

    public void sendBookingConfirmation(String to, String firstName,
                                        String courseName, String date,
                                        String startTime) {
        String subject = "Prenotazione confermata - " + courseName;
        String text = "Ciao " + firstName + "!\n\n" +
                "La tua prenotazione e' confermata:\n" +
                "Corso: " + courseName + "\n" +
                "Data: " + date + "\n" +
                "Orario: " + startTime + "\n\n" +
                "A presto!\n" +
                "Il team GymPulse";
        sendEmail(to, subject, text);
    }

    public void sendWaitlistNotification(String to, String firstName,
                                         String courseName, String date,
                                         String startTime) {
        String subject = "Posto disponibile - " + courseName;
        String text = "Buone notizie " + firstName + "!\n\n" +
                "Si e' liberato un posto per il corso:\n" +
                "Corso: " + courseName + "\n" +
                "Data: " + date + "\n" +
                "Orario: " + startTime + "\n\n" +
                "La tua prenotazione e' confermata automaticamente!\n" +
                "Il team GymPulse";
        sendEmail(to, subject, text);
    }
}