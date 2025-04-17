package ahmed.project.spring.examentdd.repositorytest;

import ahmed.project.spring.examentdd.carLocation.model.Car;
import ahmed.project.spring.examentdd.carLocation.repository.CarRepository;
import ahmed.project.spring.examentdd.carLocation.service.CarRentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRentalServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarRentalService service;

    @Test
    void shouldRentCarSuccessfully() {
        Car car = new Car("ABC-123", "Honda", true);
        when(carRepository.findByRegistrationNumber("ABC-123"))
                .thenReturn(Optional.of(car));

        boolean rented = service.rentCar("ABC-123");

        assertTrue(rented);
        verify(carRepository).updateCar(car);
        assertFalse(car.isAvailable());
    }

    @Test
    void shouldNotRentIfCarIsUnavailable() {
        Car car = new Car("XYZ-999", "Ford", false);
        when(carRepository.findByRegistrationNumber("XYZ-999"))
                .thenReturn(Optional.of(car));

        assertFalse(service.rentCar("XYZ-999"));
        verify(carRepository, never()).updateCar(any());
    }

    @Test
    void shouldCallUpdateCarAfterReturn() {
        Car car = new Car("ABC-123", "Honda", false);
        CarRepository spyRepo = spy(new CarRepository());
        spyRepo.addCar(car);
        CarRentalService service = new CarRentalService();
        ReflectionTestUtils.setField(service, "carRepository", spyRepo);

        service.returnCar("ABC-123");

        verify(spyRepo).updateCar(any(Car.class));
        assertTrue(car.isAvailable());
    }




}
