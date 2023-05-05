package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.CheckpointNotFound;
import com.truckersmpspringboot.model.Checkpoint;
import com.truckersmpspringboot.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CheckpointController {

    @Autowired
    private CheckpointRepository checkpointRepository;


    public CheckpointController(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    @GetMapping(value = "/allCheckpoints")
    public @ResponseBody Iterable<Checkpoint> getAllCheckpoints() {
        return checkpointRepository.findAll();
    }

    @PostMapping(value = "/validateCheckpoint")
    public @ResponseBody Checkpoint validateCheckpoint(@RequestBody String info) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return checkpointRepository.findCheckpointByCheckpointAddress(properties.getProperty("checkpointAddress"));
    }

    @DeleteMapping(value = "/deleteCheckpoint/{id}")
    public @ResponseBody
    String deleteCheckpoint(@PathVariable(name = "id") int id) {
        checkpointRepository.deleteById(id);

        if (checkpointRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/checkpointById")
//    public @ResponseBody Checkpoint getCheckpointById (@PathVariable(name = "id") int id) {
//        return checkpointRepository.findById(id).orElseThrow(() -> new CheckpointNotFound(id));
//    }

    @GetMapping(value = "/checkpointById/{id}")
    public EntityModel<Checkpoint> getCheckpointById(@PathVariable(name = "id") int id) {

        Checkpoint checkpoint = checkpointRepository.findById(id).orElseThrow(() -> new CheckpointNotFound(id));
        return EntityModel.of(checkpoint, linkTo(methodOn(CheckpointController.class).getCheckpointById(id)).withSelfRel(),
                linkTo(methodOn(CheckpointController.class).getAllCheckpoints()).withRel("Checkpoint"));
    }

    @PutMapping(value = "/updateCheckpoint/{id}")
    public @ResponseBody
    String updateCheckpoint(@RequestBody String checkpointInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(checkpointInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Checkpoint checkpoint = checkpointRepository.findById(id)
                .orElseThrow(() -> new CheckpointNotFound(id));
        checkpoint.setCheckpointAddress(properties.getProperty("checkpointAddress"));
        checkpoint.setArrivalDate(LocalDate.parse(properties.getProperty("arrivalDate")));
        checkpoint.setDepartureDate(LocalDate.parse(properties.getProperty("departureDate")));
        //Pabaigti

        checkpointRepository.save(checkpoint);
        return "Success";
    }


    @PostMapping(value = "/createCheckpoint")
    public @ResponseBody
    String createCheckpoint(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Checkpoint checkpoint = new Checkpoint(properties.getProperty("checkpointAddress"), LocalDate.parse(properties.getProperty("arrivalDate")), LocalDate.parse(properties.getProperty("departureDate")), null);

        checkpointRepository.save(checkpoint);
        return "Success";
    }
}
