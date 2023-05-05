package com.truckersmpspringboot.controllers;

import com.google.gson.Gson;
import com.truckersmpspringboot.error.CommentNotFound;
import com.truckersmpspringboot.model.Comment;
import com.truckersmpspringboot.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;


    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping(value = "/allComments")
    public @ResponseBody Iterable<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @PostMapping(value = "/validateComment")
    public @ResponseBody Comment validateComment(@RequestBody String info) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return commentRepository.findCommentByCommentText(properties.getProperty("commentText"));
    }

    @DeleteMapping(value = "/deleteComment/{id}")
    public @ResponseBody
    String deleteComment(@PathVariable(name = "id") int id) {
        commentRepository.deleteById(id);

        if (commentRepository.findById(id) == null) return "OK";
        return "Not OK";
    }

//    @GetMapping(value = "/commentById")
//    public @ResponseBody Comment getCommentById (@PathVariable(name = "id") int id) {
//        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFound(id));
//    }

    @GetMapping(value = "/commentById/{id}")
    public EntityModel<Comment> getCommentById(@PathVariable(name = "id") int id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFound(id));
        return EntityModel.of(comment, linkTo(methodOn(CommentController.class).getCommentById(id)).withSelfRel(),
                linkTo(methodOn(CommentController.class).getAllComments()).withRel("Comment"));
    }

    @PutMapping(value = "/updateComment/{id}")
    public @ResponseBody
    String updateComment(@RequestBody String commentInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(commentInfoToUpdate, Properties.class);

        //Issitrauksiu useri pagal id

        //Cia bus useris istrauktas, gali buti, kad jo neras, tai susikuriu sau atskira klaida, kad butu graziai pavaizduota
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound(id));
        comment.setCommentText(properties.getProperty("commentText"));
        //Pabaigti

        commentRepository.save(comment);
        return "Success";
    }


    @PostMapping(value = "/createComment")
    public @ResponseBody
    String createComment(@RequestBody String request) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(request, Properties.class);

        Comment comment = new Comment(properties.getProperty("commentText"), null, null, null, null, null);

        commentRepository.save(comment);
        return "Success";
    }
}
