package ru.kato.weatherservice;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    public static void main(String[] args) {
        //55.3333, 86.0833
        getWeather("https://api.weather.yandex.ru/v2/forecast"); //п. 1 задания
        getWeather("https://api.weather.yandex.ru/v2/forecast?lat=55.3333&lon=86.0833"); //п. 2 задания
    }

    private static void getWeather(String url) {
        HttpClient httpClient;
        httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Yandex-Weather-Key","1fbe83f3-27ba-4056-b7ed-5fd144bc8d3d")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
    }
}