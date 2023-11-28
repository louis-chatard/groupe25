package eu.telecomnancy.weather;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import javax.persistence.Id;

@Component
@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    City city;

    double temperature;
    double humidity;
    double pressure;
    double windSpeed;
    double windDirection;
    double cloudiness;
    double rain;
    double snow;
    long timestamp;

    public WeatherData() {
    }

    public WeatherData(City city, String country, double temperature, double humidity, double pressure,
            double windSpeed, double windDirection, double cloudiness, double rain, double snow, long timestamp) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.cloudiness = cloudiness;
        this.rain = rain;
        this.snow = snow;
        this.timestamp = timestamp;
    }

    public WeatherData(JsonObject response) {
        this.timestamp = response.getAsJsonObject("dt").getAsLong();
        this.city = new City(response);
        this.temperature = (response.getAsJsonObject("main")).getAsJsonPrimitive("temp").getAsDouble();
        this.humidity = (response.getAsJsonObject("main")).getAsJsonPrimitive("humidity").getAsDouble();
        this.pressure = (response.getAsJsonObject("main")).getAsJsonPrimitive("pressure").getAsDouble();
        this.windSpeed = (response.getAsJsonObject("wind")).getAsJsonPrimitive("speed").getAsDouble();
        this.windDirection = (response.getAsJsonObject("wind")).getAsJsonPrimitive("deg").getAsDouble();
        this.cloudiness = (response.getAsJsonObject("cloud")).getAsJsonPrimitive("all").getAsDouble();
        try {
            this.rain = (response.getAsJsonObject("snow")).getAsJsonPrimitive("1h").getAsDouble();
        } finally {
            this.rain = 0;
            try {
                this.snow = (response.getAsJsonObject("rain")).getAsJsonPrimitive("1h").getAsDouble();
            } finally {
                this.snow = 0;
            }
        }
//        return new WeatherData(city, city.getCountry(), temperature, humidity, pressure, windSpeed, windDirection, cloudiness, rain, snow, timestamp);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "WeatherData [id=" + id + ", city=" + city + ", temperature=" + temperature + ", humidity=" + humidity
                + ", pressure=" + pressure + ", windSpeed=" + windSpeed + ", windDirection=" + windDirection
                + ", cloudiness=" + cloudiness + ", rain=" + rain + ", snow=" + snow + ", timestamp=" + timestamp + "]";
    }
}
