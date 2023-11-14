package eu.telecomnancy.weather;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EntityScan(basePackages = "eu.telecomnancy.weather")
public class WeatherDataTest {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Test
    public void testInsertWeatherData() {
        City city = new City();
        city.setName("Lille");
        city.setCountry("France");
        city.setLatitude(50.6333);
        city.setLongitude(3.0667);
        cityRepository.save(city);
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setTemperature(10);
        weatherData.setHumidity(20);
        weatherData.setPressure(30);
        weatherData.setWindSpeed(40);
        weatherData.setWindDirection(50);
        weatherData.setCloudiness(60);
        weatherData.setTimestamp(110);
        this.weatherDataRepository.save(weatherData);
        assertEquals(cityRepository.findByName("Lille").getName(), "Lille");

    }

    @Test
    public void testInsertANewCity() {
        City city1 = new City();
        city1.setName("Nancy");
        city1.setCountry("France");
        city1.setLatitude(48.6833);
        city1.setLongitude(6.2);
        City city2 = new City();
        city2.setName("Strasbourg");
        city2.setCountry("France");
        city2.setLatitude(48.5833);
        city2.setLongitude(7.75);
        cityRepository.save(city1);
        cityRepository.save(city2);
        assertEquals(cityRepository.findByName("Nancy").getName(), "Nancy");
        assertEquals(cityRepository.findByName("Strasbourg").getName(), "Strasbourg");

    }
    
}
