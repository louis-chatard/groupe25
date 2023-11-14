package eu.telecomnancy.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OpenWeatherClientTest {
    
    @Test
    public void testGetWeather() throws Exception {
        OpenWeatherClient client = new OpenWeatherClient();
        String response=client.getWeather("Lille");
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        assertEquals("Lille", jsonObject.get("name").getAsString());
    }

    @Test
    public void testGetWeather2() throws Exception {
        OpenWeatherClient client = new OpenWeatherClient();
        String response=client.getWeather("xxxx");
        assertEquals("404", response);
    }
}
