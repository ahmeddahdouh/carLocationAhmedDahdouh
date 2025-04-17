package ahmed.project.spring.examentdd.CarstepDefs;

import ahmed.project.spring.examentdd.carLocation.model.Car;
import ahmed.project.spring.examentdd.carLocation.repository.CarRepository;
import ahmed.project.spring.examentdd.carLocation.service.CarRentalService;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarSteps {

    private CarRepository carRepository;
    private CarRentalService carRentalService;
    private List<Car> availableCars;
    private Car testCar;

    @Before
    public void setUp() {
        carRepository = new CarRepository();
        carRentalService = new CarRentalService(carRepository); // injection ici
    }

    // Scenario 1
    @Given("des voitures sont disponibles")
    public void des_voitures_sont_disponibles() {
        Car car1 = new Car("ABC-123", "Renault", true);
        Car car2 = new Car("XYZ-789", "Peugeot", true);
        carRepository.addCar(car1);
        carRepository.addCar(car2);
    }

    @When("je demande la liste des voitures")
    public void je_demande_la_liste_des_voitures() {
        availableCars = carRentalService.getAvailableCars();
    }

    @Then("toutes les voitures sont affichées")
    public void toutes_les_voitures_sont_affichées() {
        assertEquals(2, availableCars.size());
        assertTrue(availableCars.stream().allMatch(Car::isAvailable));
    }

    // Scenario 2
    @Given("une voiture est disponible")
    public void une_voiture_est_disponible() {
        testCar = new Car("RENT-001", "Toyota", true);
        carRepository.addCar(testCar);
    }

    @When("je loue cette voiture")
    public void je_loue_cette_voiture() {
        carRentalService.rentCar("RENT-001");
    }

    @Then("la voiture n'est plus disponible")
    public void la_voiture_n_est_plus_disponible() {
        Car updatedCar = carRepository.findByRegistrationNumber("RENT-001").orElseThrow();
        assertFalse(updatedCar.isAvailable());
    }

    // Scenario 3
    @Given("une voiture est louée")
    public void une_voiture_est_louee() {
        testCar = new Car("RENT-002", "Dacia", false);
        carRepository.addCar(testCar);
    }

    @When("je retourne cette voiture")
    public void je_retourne_cette_voiture() {
        carRentalService.returnCar("RENT-002");
    }

    @Then("la voiture est marquée comme disponible")
    public void la_voiture_est_marquee_comme_disponible() {
        Car updatedCar = carRepository.findByRegistrationNumber("RENT-002").orElseThrow();
        assertTrue(updatedCar.isAvailable());
    }
}
