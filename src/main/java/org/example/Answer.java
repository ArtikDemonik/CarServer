package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Car{
    int id, price;
    String name, type, brand, power, description;

    public Car(int id, String name, String type, String brand, String power, String description, int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.power = power;
        this.description = description;
        this.price = price;
    }
}

public class Answer {
    List<Car> cars;
    Answer(List<Car> cars){
        this.cars = cars;
    }
}
