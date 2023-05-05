package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.CarNotFound;
import com.truckersmpspringboot.model.Car;
import com.truckersmpspringboot.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;


    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping(value = "/allCars")
    public @ResponseBody Iterable<Car> getAllCars() {
        return carRepository.findAll();
    }

    @PostMapping(value = "/validateCar")
    public @ResponseBody Car validateCar(@RequestBody String info) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return carRepository.findCarByPlateNumber(properties.getProperty("plateNumber"));
    }

    @DeleteMapping(value = "/deleteCar/{id}")
    public @ResponseBody
    String deleteCar(@PathVariable(name = "id") int id) {
        carRepository.deleteById(id);

        if (carRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/carById")
//    public @ResponseBody Car getCarById (@PathVariable(name = "id") int id) {
//        return carRepository.findById(id).orElseThrow(() -> new CarNotFound(id));
//    }

    @GetMapping(value = "/carById/{id}")
    public EntityModel<Car> getCarById(@PathVariable(name = "id") int id) {

        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFound(id));
        return EntityModel.of(car, linkTo(methodOn(CarController.class).getCarById(id)).withSelfRel(),
                linkTo(methodOn(CarController.class).getAllCars()).withRel("Car"));
    }

    @PutMapping(value = "/updateCar/{id}")
    public @ResponseBody
    String updateCar(@RequestBody String carInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(carInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new CarNotFound(id));
        car.setPlateNumber(properties.getProperty("plateNumber"));
        car.setCarModel(properties.getProperty("carModel"));
        car.setWeight(properties.getProperty("weight"));
        car.setHeight(properties.getProperty("height"));
        //Pabaigti

        carRepository.save(car);
        return "Success";
    }


    @PostMapping(value = "/createCar")
    public @ResponseBody
    String createCar(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Car car = new Car(properties.getProperty("plateNumber"), properties.getProperty("carModel"), properties.getProperty("weight"), properties.getProperty("height"), null, false, null);

        carRepository.save(car);
        return "Success";
    }
}
