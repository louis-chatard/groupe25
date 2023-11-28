package eu.telecomnancy.weather;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import com.google.gson.JsonObject;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    String country;
    double latitude;
    double longitude;

    @OneToMany(mappedBy = "city")
    List<WeatherData> weatherData;

    public City() {
    }
    
    public City(String name, String country, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(JsonObject response) {
        this.name = response.get("name").getAsString();
        this.country = response.get("sys").getAsJsonObject().get("country").getAsString();
        this.latitude = response.get("coord").getAsJsonObject().get("lat").getAsDouble();
        this.longitude = response.get("coord").getAsJsonObject().get("lon").getAsDouble();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    @Override
    public String toString() {
        return "City [id=" + id + ", name=" + name + ", country=" + country + ", latitude=" + latitude + ", longitude="
                + longitude + "]";
    }
}
