package ahmed.project.spring.examentdd.controllerTest;

import ahmed.project.spring.examentdd.carLocation.controller.CarController;
import ahmed.project.spring.examentdd.carLocation.model.Car;
import ahmed.project.spring.examentdd.carLocation.service.CarRentalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CarController.class)
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CarRentalService service;

    @Test
    void shouldGetAllCars() throws Exception {
        List<Car> cars = List.of(new Car("1", "Toyota", true));
        when(service.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Toyota"));
    }

    @Test
    void shouldRentCar() throws Exception {
        when(service.rentCar("123")).thenReturn(true);

        mockMvc.perform(post("/cars/rent/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
