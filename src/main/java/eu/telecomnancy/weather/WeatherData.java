package eu.telecomnancy.weather;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

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

    public WeatherData(long id, City city, String country, double temperature, double humidity, double pressure,
            double windSpeed, double windDirection, double cloudiness, double rain, double snow, long timestamp) {
        this.id = id;
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
