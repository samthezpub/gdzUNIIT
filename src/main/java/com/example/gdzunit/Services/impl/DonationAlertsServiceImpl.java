package com.example.gdzunit.Services.impl;

import com.example.gdzunit.Services.DonationAlertsService;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class DonationAlertsServiceImpl implements DonationAlertsService {
    private final String clientId = "12025";
    private final String clientSecret = "C2bmlIVnaxb8paDJggjJyc4N8c2hFYlgnY6PzO4t";
    private final String redirect_uri = "http://localhost:8080/dalerts/getcode";
    private String scope = "oauth-donation-index";

    private String accessToken;


    public String getAccessToken() {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpGet = new HttpPost("https://www.donationalerts.com/oauth/token?" +
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

            return result.toString(); // Замените на реальный код извлечения токена
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String authorize() {
        return null;
    }
}
