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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        CarRentalService service = new CarRentalService(carRepository);
        ReflectionTestUtils.setField(service, "carRepository", spyRepo);

        service.returnCar("ABC-123");

        verify(spyRepo).updateCar(any(Car.class));
        assertTrue(car.isAvailable());
    }

    @Test
    void shouldAddCarIfNotExists() {
        Car car = new Car("NEW-001", "Tesla", true);
        when(carRepository.findByRegistrationNumber("NEW-001")).thenReturn(Optional.empty());

        service.addCar(car);

        verify(carRepository).addCar(car);
    }



    @Test
    void shouldNotAddCarIfAlreadyExists() {
        Car existing = new Car("NEW-001", "Tesla", true);
        when(carRepository.findByRegistrationNumber("NEW-001")).thenReturn(Optional.of(existing));

        assertThrows(RuntimeException.class, () -> service.addCar(existing));
        verify(carRepository, never()).addCar(existing);
    }

    //partie 2

    @Test
    void shouldFindCarsByModel() {
        List<Car> cars = List.of(
                new Car("1", "Toyota", true),
                new Car("2", "Toyota", false)
        );
        when(carRepository.getAllCars()).thenReturn(cars);

        List<Car> result = service.searchByModel("Toyota");

        assertEquals(2, result.size());
    }








}
