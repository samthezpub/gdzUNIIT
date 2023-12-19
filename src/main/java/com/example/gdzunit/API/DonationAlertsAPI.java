package com.example.gdzunit.API;

import lombok.Data;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Data
@Component
public class DonationAlertsAPI {
    private static final String clientId = "12025";
    private static final String clientSecret = "C2bmlIVnaxb8paDJggjJyc4N8c2hFYlgnY6PzO4t";
    private final String redirect_uri = "http://localhost:8080/dalerts";
    private static String accessToken = "RJlyXApNLilcvDmEoaOT";
    private static String scope = "oauth-donation-index";

    public DonationAlertsAPI() {
        this.accessToken = authorize();
    }

    private String authorize() {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://www.donationalerts.com/oauth/authorize?" +
                    "client_id=" + clientId +
                    "&redirect_uri=" + redirect_uri +
                    "&response_type=code" +
                    "&scope=" + scope
            );

            System.out.println(httpGet);
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // Обработка ответа и извлечение токена
            // Пример: {"access_token":"your_access_token","token_type":"bearer","expires_in":3600}
            // Реальный код может зависеть от структуры ответа API
            // ...

            return "your_access_token"; // Замените на реальный код извлечения токена
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
