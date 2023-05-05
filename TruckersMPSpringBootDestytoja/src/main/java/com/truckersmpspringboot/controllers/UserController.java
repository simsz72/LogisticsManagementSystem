package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.DriverManagerNotFound;
import com.truckersmpspringboot.error.DriverNotFound;
import com.truckersmpspringboot.model.Driver;
import com.truckersmpspringboot.model.DriverManager;
import com.truckersmpspringboot.model.Role;
import com.truckersmpspringboot.repository.DriverRepository;
import com.truckersmpspringboot.repository.DriverManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverManagerRepository driverManagerRepository;


    public UserController(DriverRepository driverRepository, DriverManagerRepository driverManagerRepository) {
        this.driverRepository = driverRepository;
        this.driverManagerRepository = driverManagerRepository;
    }

    @GetMapping(value = "/allDrivers")
    public @ResponseBody Iterable<Driver> getAllDrivers(){
        return driverRepository.findAll();
    }

    @PostMapping(value = "/validateDriver")
    public @ResponseBody Driver validateDriver(@RequestBody String info){
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return driverRepository.findDriverByLoginAndPassword(properties.getProperty("login"), properties.getProperty("password"));
    }

    @DeleteMapping(value = "/deleteDriver/{id}")
    public @ResponseBody
    String deleteDriver(@PathVariable(name = "id") int id){
        driverRepository.deleteById(id);

        if(driverRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/driverById")
//    public @ResponseBody Driver getDriverById (@PathVariable(name = "id") int id) {
//        return driverRepository.findById(id).orElseThrow(() -> new DriverNotFound(id));
//    }

    @GetMapping(value = "/driverById/{id}")
    public EntityModel<Driver> getDriverById (@PathVariable(name = "id") int id) {

        Driver driver = driverRepository.findById(id).orElseThrow(() -> new DriverNotFound(id));
        return EntityModel.of(driver, linkTo(methodOn(UserController.class).getDriverById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllDrivers()).withRel("Driver"));
    }

    @PutMapping(value = "/updateDriver/{id}")
    public @ResponseBody
    String updateDriver(@RequestBody String driverInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(driverInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFound(id));
        driver.setLogin(properties.getProperty("login"));
        driver.setPassword(properties.getProperty("password"));
        driver.setName(properties.getProperty("name"));
        driver.setSurname(properties.getProperty("surname"));
        //Pabaigti

        driverRepository.save(driver);
        return "Success";
    }
    @PostMapping(value = "/createDriver")
    public @ResponseBody
    String createDriver(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Driver driver = new Driver(Role.DRIVER, properties.getProperty("login"), properties.getProperty("password"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("phone"), properties.getProperty("email")
                , LocalDate.parse(properties.getProperty("birthDate")), null, null, null, false);

        driverRepository.save(driver);
        return "Success";
    }


    //MANAGER

    @GetMapping(value = "/allDriverManagers")
    public @ResponseBody Iterable<DriverManager> getAllDriverManagers(){
        return driverManagerRepository.findAll();
    }


    @PostMapping(value = "/validateDriverManager")
    public @ResponseBody DriverManager validateDriverManager(@RequestBody String info){
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return driverManagerRepository.findDriverManagerByLoginAndPassword(properties.getProperty("login"), properties.getProperty("password"));
    }

    @DeleteMapping(value = "/deleteDriverManager/{id}")
    public @ResponseBody
    String deleteDriverManager(@PathVariable(name = "id") int id){
        driverManagerRepository.deleteById(id);

        if(driverManagerRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/driverManagerById")
//    public @ResponseBody DriverManager getDriverManagerById (@PathVariable(name = "id") int id) {
//        return driverManagerRepository.findById(id).orElseThrow(() -> new DriverManagerNotFound(id));
//    }

    @GetMapping(value = "/driverManagerById/{id}")
    public EntityModel<DriverManager> getDriverManagerById (@PathVariable(name = "id") int id) {

        DriverManager driverManager = driverManagerRepository.findById(id).orElseThrow(() -> new DriverManagerNotFound(id));
        return EntityModel.of(driverManager, linkTo(methodOn(UserController.class).getDriverManagerById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllDriverManagers()).withRel("DriverManager"));
    }

    @PutMapping(value = "/updateDriverManager/{id}")
    public @ResponseBody
    String updateDriverManager(@RequestBody String driverManagerInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(driverManagerInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        DriverManager driverManager = driverManagerRepository.findById(id)
                .orElseThrow(() -> new DriverManagerNotFound(id));
        driverManager.setLogin(properties.getProperty("login"));
        driverManager.setPassword(properties.getProperty("password"));
        driverManager.setName(properties.getProperty("name"));
        driverManager.setSurname(properties.getProperty("surname"));
        //Pabaigti

        driverManagerRepository.save(driverManager);
        return "Success";
    }


    @PostMapping(value = "/createDriverManager")
    public @ResponseBody
    String createDriverManager(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        DriverManager driverManager = new DriverManager(Role.DRIVERMANAGER, properties.getProperty("login"), properties.getProperty("password"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("phone"), properties.getProperty("email")
                , LocalDate.parse(properties.getProperty("birthDate")), null, null, null, null, null,  false);

        driverManagerRepository.save(driverManager);
        return "Success";
    }


}
