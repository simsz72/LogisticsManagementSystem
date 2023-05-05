package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.ForumNotFound;
import com.truckersmpspringboot.model.Forum;
import com.truckersmpspringboot.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ForumController {

    @Autowired
    private ForumRepository forumRepository;


    public ForumController(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @GetMapping(value = "/allForums")
    public @ResponseBody Iterable<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    @PostMapping(value = "/validateForum")
    public @ResponseBody Forum validateForum(@RequestBody String info) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return forumRepository.findForumByForumTitle(properties.getProperty("forumTitle"));
    }

    @DeleteMapping(value = "/deleteForum/{id}")
    public @ResponseBody
    String deleteForum(@PathVariable(name = "id") int id) {
        forumRepository.deleteById(id);

        if (forumRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/forumById")
//    public @ResponseBody Forum getForumById (@PathVariable(name = "id") int id) {
//        return forumRepository.findById(id).orElseThrow(() -> new ForumNotFound(id));
//    }

    @GetMapping(value = "/forumById/{id}")
    public EntityModel<Forum> getForumById(@PathVariable(name = "id") int id) {

        Forum forum = forumRepository.findById(id).orElseThrow(() -> new ForumNotFound(id));
        return EntityModel.of(forum, linkTo(methodOn(ForumController.class).getForumById(id)).withSelfRel(),
                linkTo(methodOn(ForumController.class).getAllForums()).withRel("Forum"));
    }

    @PutMapping(value = "/updateForum/{id}")
    public @ResponseBody
    String updateForum(@RequestBody String forumInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(forumInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new ForumNotFound(id));
        forum.setForumTitle(properties.getProperty("forumTitle"));
        //Pabaigti

        forumRepository.save(forum);
        return "Success";
    }


    @PostMapping(value = "/createForum")
    public @ResponseBody
    String createForum(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Forum forum = new Forum(properties.getProperty("forumTitle"), null, null, null);

        forumRepository.save(forum);
        return "Success";
    }
}
