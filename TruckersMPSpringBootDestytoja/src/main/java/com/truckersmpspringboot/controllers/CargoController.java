package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.CargoNotFound;
import com.truckersmpspringboot.model.Cargo;
import com.truckersmpspringboot.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;


    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping(value = "/allCargos")
    public @ResponseBody Iterable<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    @PostMapping(value = "/validateCargo")
    public @ResponseBody Cargo validateCargo(@RequestBody String info) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return cargoRepository.findCargoByCargoName(properties.getProperty("cargoName"));
    }

    @DeleteMapping(value = "/deleteCargo/{id}")
    public @ResponseBody
    String deleteCargo(@PathVariable(name = "id") int id) {
        cargoRepository.deleteById(id);

        if (cargoRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/cargoById")
//    public @ResponseBody Cargo getCargoById (@PathVariable(name = "id") int id) {
//        return cargoRepository.findById(id).orElseThrow(() -> new CargoNotFound(id));
//    }

    @GetMapping(value = "/cargoById/{id}")
    public EntityModel<Cargo> getCargoById(@PathVariable(name = "id") int id) {

        Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new CargoNotFound(id));
        return EntityModel.of(cargo, linkTo(methodOn(CargoController.class).getCargoById(id)).withSelfRel(),
                linkTo(methodOn(CargoController.class).getAllCargos()).withRel("Cargo"));
    }

    @PutMapping(value = "/updateCargo/{id}")
    public @ResponseBody
    String updateCargo(@RequestBody String cargoInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(cargoInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new CargoNotFound(id));
        cargo.setHeight(properties.getProperty("height"));
        cargo.setWeight(properties.getProperty("weight"));
        cargo.setCargoName(properties.getProperty("cargoName"));
        //Pabaigti

        cargoRepository.save(cargo);
        return "Success";
    }


    @PostMapping(value = "/createCargo")
    public @ResponseBody
    String createCargo(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Cargo cargo = new Cargo(properties.getProperty("height"), properties.getProperty("weight"), properties.getProperty("cargoName"), null, null, null, null, null);

        cargoRepository.save(cargo);
        return "Success";
    }
}
