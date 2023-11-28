package eu.telecomnancy.weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByCity(City city);
    List<WeatherData> findById(long id);
    List<WeatherData> findByCloudiness(double cloudiness);
    List<WeatherData> findByHumidity(double humidity);
    List<WeatherData> findByPressure(double pressure);
    List<WeatherData> findByRain(double rain);
    List<WeatherData> findBySnow(double snow);
    
}
