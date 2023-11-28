package eu.telecomnancy.weather;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);
    List<City> findByLatitude(double latitude);
    List<City> findByLongitude(double longitude);
    
}
