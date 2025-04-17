package ahmed.project.spring.examentdd.carLocation.model;

public class Car {
    private String registrationNumber;
    private String model;
    private boolean available;

    public Car(String registrationNumber, String model, boolean available) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.available = available;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}