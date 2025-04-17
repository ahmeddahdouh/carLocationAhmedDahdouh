package ahmed.project.spring.examentdd.carLocation.controller;

import ahmed.project.spring.examentdd.carLocation.model.Car;
import ahmed.project.spring.examentdd.carLocation.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarRentalService carRentalService;

    @GetMapping
    public List<Car> getAllCars() {
        return carRentalService.getAllCars();
    }

    @PostMapping("/rent/{registrationNumber}")
    public ResponseEntity<Boolean> rentCar(@PathVariable String registrationNumber) {
        boolean result = carRentalService.rentCar(registrationNumber);
        return ResponseEntity.ok(result); // Retourne un ResponseEntity avec la valeur booléenne
    }

    @PostMapping("/return/{registrationNumber}")
    public ResponseEntity<Void> returnCar(@PathVariable String registrationNumber) {
        carRentalService.returnCar(registrationNumber);
        return ResponseEntity.ok().build(); // Retourne un statut OK sans contenu
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addCar(@RequestBody Car car) {
        boolean result = carRentalService.addCar(car);
        return ResponseEntity.ok(result); // Retourne un ResponseEntity avec la valeur booléenne
    }
}
