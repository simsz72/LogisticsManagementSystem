package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.DestinationNotFound;
import com.truckersmpspringboot.model.Destination;
import com.truckersmpspringboot.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DestinationController {

    @Autowired
    private DestinationRepository destinationRepository;


    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping(value = "/allDestinations")
    public @ResponseBody Iterable<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @PostMapping(value = "/validateDestination")
    public @ResponseBody Destination validateDestination(@RequestBody String info) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return destinationRepository.findDestinationByPlaceAddress(properties.getProperty("placeAddress"));
    }

    @DeleteMapping(value = "/deleteDestination/{id}")
    public @ResponseBody
    String deleteDestination(@PathVariable(name = "id") int id) {
        destinationRepository.deleteById(id);

        if (destinationRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/destinationById")
//    public @ResponseBody Destination getDestinationById (@PathVariable(name = "id") int id) {
//        return destinationRepository.findById(id).orElseThrow(() -> new DestinationNotFound(id));
//    }

    @GetMapping(value = "/destinationById/{id}")
    public EntityModel<Destination> getDestinationById(@PathVariable(name = "id") int id) {

        Destination destination = destinationRepository.findById(id).orElseThrow(() -> new DestinationNotFound(id));
        return EntityModel.of(destination, linkTo(methodOn(DestinationController.class).getDestinationById(id)).withSelfRel(),
                linkTo(methodOn(DestinationController.class).getAllDestinations()).withRel("Destination"));
    }

    @PutMapping(value = "/updateDestination/{id}")
    public @ResponseBody
    String updateDestination(@RequestBody String destinationInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(destinationInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFound(id));
        destination.setPlaceAddress(properties.getProperty("placeAddress"));
        //Pabaigti

        destinationRepository.save(destination);
        return "Success";
    }


    @PostMapping(value = "/createDestination")
    public @ResponseBody
    String createDestination(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Destination destination = new Destination(properties.getProperty("placeAddress"), null, null, null, null, null);

        destinationRepository.save(destination);
        return "Success";
    }
}
