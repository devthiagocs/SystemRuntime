package com.systemruntime;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import org.json.JSONObject;

public class ObterHorario {
    private static final String TIMEZONE = "America%2FSao_Paulo";
    private static final String URL = "https://timeapi.io/api/v1/time/current/zone?timezone=" + TIMEZONE;

    public static LocalTime obterHorario() {
        LocalTime horaAtual = null;
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .timeout(Duration.ofSeconds(5))
                    .header("Accept", "*/*")
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
            Configuracao.log("Erro na requisição: " + e);
        }
        return horaAtual;
    }

    public static LocalTime obterHorarioComTentativas(int tentativas, int pausaSegundos) throws InterruptedException {
        for (int i = 1; i <= tentativas; i++) {
            LocalTime hora = obterHorario();
            if (hora != null) {
                return hora;
            }
            System.out.println("Tentativa " + i + " de " + tentativas + " falhou.");
            if (i < tentativas) {
                Thread.sleep(pausaSegundos * 1000L);
            }
        }
        return null;
    }
}
