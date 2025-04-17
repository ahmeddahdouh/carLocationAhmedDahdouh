package ahmed.project.spring.examentdd.repositorytest;

import ahmed.project.spring.examentdd.carLocation.model.Car;
import ahmed.project.spring.examentdd.carLocation.repository.CarRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarRepositoryTest {


    @Test
    void shouldAddAndFindCarByRegistrationNumber() {
        CarRepository repo = new CarRepository();
        Car car = new Car("123-ABC", "Toyota", true);
        repo.addCar(car);

        Optional<Car> found = repo.findByRegistrationNumber("123-ABC");
        assertTrue(found.isPresent());
        assertEquals("Toyota", found.get().getModel());
    }





}
