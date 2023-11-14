# Creating Spring Service

In this part, I will show you how to create a simple Spring service that calls the OpenWeatherMap API to get the weather data for a given city.

1. **Create a new Spring Boot project**: You can use Spring Initializr (https://start.spring.io/) to create a new Spring Boot project. You need to select the necessary dependencies based on your project needs. For a simple service, you might need "Spring Web" and "Spring Data JPA" for web and database operations respectively.

2. **Create a Service**: This is a class where you write your business logic. You can use the @Service annotation to indicate that it's a service class. This class should have a field for the Repository you created, which can be automatically injected by Spring using the @Autowired annotation. You can also use the @Transactional annotation to indicate that the methods in this class should be executed in a transaction.

6. **Run the Application**: You can run the application using the main method in the main class that was created when you created the project. If everything is set up correctly, your service should be up and running.


Here is a visual representation of the process:

[![The Process](https://mermaid.ink/img/pako:eNqNkM1uAjEMhF_Fcq-7UuFSKUiVWOi96vZUwiFKDJuSjaPgtEWIdyf0B7W33uyZz5bHR7TsCBVus0kDPHc6AsxXGheZjBD0Kfu4hY5Z4DHzK1nRuIa2vYfuF0X5zVuqzmW8-7QX1X4qEeYpBW-NeI7ffvBx18shENzCXjLvSN1Mybq7SfPVtu_eyaCm6WP2l5_8g9cRGxwpj8a7mut42aBRBhrrfaqWjjamhBpDx1NFTRHuD9GiklyowZJcjbT0pn5kRLUxYX9VH5wXzlcxmfjC_AOdzmrycCs?type=png)](https://mermaid.live/edit#pako:eNqNkM1uAjEMhF_Fcq-7UuFSKUiVWOi96vZUwiFKDJuSjaPgtEWIdyf0B7W33uyZz5bHR7TsCBVus0kDPHc6AsxXGheZjBD0Kfu4hY5Z4DHzK1nRuIa2vYfuF0X5zVuqzmW8-7QX1X4qEeYpBW-NeI7ffvBx18shENzCXjLvSN1Mybq7SfPVtu_eyaCm6WP2l5_8g9cRGxwpj8a7mut42aBRBhrrfaqWjjamhBpDx1NFTRHuD9GiklyowZJcjbT0pn5kRLUxYX9VH5wXzlcxmfjC_AOdzmrycCs)




## OpenWeatherMap Service

Here is an example of a simple Spring service that calls the OpenWeatherMap API to get the weather data for a given city:

```java
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_KEY = "your_api_key_here";
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";

    public String getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(WEATHER_URL, String.class, city, API_KEY);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to get weather data");
        }
    }
}
```

In this example, we have a `WeatherService` class that has a `getWeather` method. This method takes a city name as a parameter and makes a GET request to the OpenWeatherMap API to get the weather data for that city.

The `getForEntity` method of `RestTemplate` is used to make the GET request. This method takes the URL of the API, the type of the response body, and any URL variables as parameters.

The API key for OpenWeatherMap is stored in the `API_KEY` constant. You should replace `"your_api_key_here"` with your actual OpenWeatherMap API key.

The `getWeather` method returns the body of the response if the request is successful (i.e., if the status code of the response is in the 200 range). If the request is not successful, it throws a `RuntimeException`.

Please note that this is a very basic example. In a real-world application, you would likely want to add more error handling and possibly use a more complex method for managing your API keys.

## A Client for the Weather Service

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class WeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(WeatherService weatherService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter a city name (or 'quit' to exit):");
                String city = scanner.nextLine();

                if ("quit".equalsIgnoreCase(city)) {
                    break;
                }

                try {
                    String weather = weatherService.getWeather(city);
                    System.out.println("Weather in " + city + ": " + weather);
                } catch (Exception e) {
                    System.out.println("Failed to get weather data: " + e.getMessage());
                }
            }

            scanner.close();
        };
    }
}

In this example, we have a `WeatherApplication` class that has a `main` method. This method creates a `WeatherService` object and uses it to get the weather data for a given city.

## Storing the Weather Data in a Database

Separating concerns into different services makes your code more modular and easier to maintain. Here's how you might do it:

1. **Create a Persistence Service**: This service will handle all database operations. It will have a method for saving weather data.

2. **Update the Weather Service**: The Weather Service will now only be responsible for fetching weather data. It will return the data instead of saving it.

3. **Create a new Service**: This service will use both the Weather Service and the Persistence Service. It will fetch the weather data, then pass it to the Persistence Service to be saved.

Here's a simple example:

```java
@Service
public class PersistenceService {

    private final WeatherDataRepository weatherDataRepository;

    public PersistenceService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public void saveWeatherData(String city, String weather) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setWeather(weather);
        weatherData.setRequestTime(LocalDateTime.now());

        weatherDataRepository.save(weatherData);
    }
}

@Service
public class WeatherService {

    // ...

    public String getWeather(String city) {
        // ...

        return response.getBody();
    }
}

@Service
public class WeatherAppService {

    private final WeatherService weatherService;
    private final PersistenceService persistenceService;

    public WeatherAppService(WeatherService weatherService, PersistenceService persistenceService) {
        this.weatherService = weatherService;
        this.persistenceService = persistenceService;
    }

    public void getAndSaveWeather(String city) {
        String weather = weatherService.getWeather(city);
        persistenceService.saveWeatherData(city, weather);
    }
}
```

In this example, `PersistenceService` is a new service that handles saving weather data. `WeatherService` has been updated to only fetch weather data. `WeatherAppService` is a new service that uses both `WeatherService` and `PersistenceService`. It has a `getAndSaveWeather` method that fetches the weather data for a city and then saves it.

This design allows each service to focus on a single responsibility, which makes your code easier to understand and maintain.

## Mock and Test

To test the `WeatherAppService` with a mock of the `WeatherService`, you can use a framework like Mockito. Mockito allows you to create and configure mock objects. Here's an example of how you might write the test:

```java
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class WeatherAppServiceTest {

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private PersistenceService persistenceService;

    @Test
    public void testGetAndSaveWeather() {
        // Arrange
        String city = "London";
        String weather = "Sunny";
        Mockito.when(weatherService.getWeather(city)).thenReturn(weather);

        // Act
        WeatherAppService weatherAppService = new WeatherAppService(weatherService, persistenceService);
        weatherAppService.getAndSaveWeather(city);

        // Assert
        Mockito.verify(weatherService, Mockito.times(1)).getWeather(city);
        Mockito.verify(persistenceService, Mockito.times(1)).saveWeatherData(city, weather);
    }
}
```

In this test, we're using the `@MockBean` annotation to create mock objects for `WeatherService` and `PersistenceService`. In the `testGetAndSaveWeather` method, we're setting up the mock `WeatherService` to return a specific weather string when its `getWeather` method is called. Then we're creating a `WeatherAppService` and calling its `getAndSaveWeather` method. Finally, we're verifying that the `getWeather` method of `WeatherService` and the `saveWeatherData` method of `PersistenceService` were each called exactly once with the expected parameters.

Please note that this is a very basic example. 

Create more tests to cover all possible scenarios including error handling.

## Conclusion

In this part, we have learned how to create a simple weather app using Spring Boot. We have also learned how to use the OpenWeatherMap API to get weather data for a given city. Finally, we have learned how to store the weather data in a database. 