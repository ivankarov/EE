package db.models;

import java.util.List;

public class User {
    private Integer id;
    private String name ;
    private String lastName;
    private List<Car> cars;

    public User() {
    }

    public User(Integer id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public User(Integer id, String name, String lastName, List<Car> cars) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.cars = cars;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
