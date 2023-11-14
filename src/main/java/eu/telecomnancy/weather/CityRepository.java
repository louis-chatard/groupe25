package eu.telecomnancy.weather;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);
    
}
