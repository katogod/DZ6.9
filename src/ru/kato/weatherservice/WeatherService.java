package ru.kato.weatherservice;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {

    public static final String HEADER = "X-Yandex-Weather-Key";
    public static final String HEADER_VALUE = "1fbe83f3-27ba-4056-b7ed-5fd144bc8d3d";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) {
        //55.3333, 86.0833
        WeatherService service = new WeatherService();

        String result1 = service.getWeather("https://api.weather.yandex.ru/v2/forecast"); //п. 1 задания
        System.out.println(result1);

        String result2 = service.getWeather("https://api.weather.yandex.ru/v2/forecast?lat=55.3333&lon=86.0833"); //п. 2 задания
        System.out.println(result2);

        JSONObject jsonObject = new JSONObject(result2);
        System.out.println("Текущая температура в Кемерово: " + jsonObject.getJSONObject("fact").getInt("temp"));

        int limit = 5;
        String result3 = service.getWeather("https://api.weather.yandex.ru/v2/forecast?lat=55.3333&lon=86.0833&limit=" + limit);
        JSONObject jsonObject2 = new JSONObject(result3);
        JSONArray forecasts = jsonObject2.getJSONArray("forecasts");
        int sum = 0;
        for (int i = 0; i < limit; i++) {
            sum = sum + forecasts.getJSONObject(i).getJSONObject("parts").getJSONObject("day").getInt("temp_avg");
        }
        System.out.println("Средняя температура: " + sum / limit);

    }

    public String getWeather(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(HEADER, HEADER_VALUE)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
            throw new RuntimeException("Ошибка при запросе погоды", e);
        }
    }
}