package com.systemruntime;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import org.json.JSONObject;

public class ObterHorario {
    public static LocalTime obterHorario() {
        String timezone = "America%2FSao_Paulo";
        String url = "https://timeapi.io/api/v1/time/current/zone?timezone=" + timezone;
        LocalTime horaAtual = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "accept: */*")
                    .GET()
                    .build();

            HttpResponse<String> response = client
                    .send(request, BodyHandlers.ofString());

            String resposta = response.body();

            JSONObject json = new JSONObject(resposta);
            String horaAtualString = json.getString("date_time");
            horaAtual = OffsetDateTime.parse(horaAtualString).toLocalTime().withSecond(0).withNano(0);
            System.out.println(horaAtual);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return horaAtual;
    }
}
