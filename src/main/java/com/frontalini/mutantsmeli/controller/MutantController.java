package com.frontalini.mutantsmeli.controller;

import com.frontalini.mutantsmeli.services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
public class MutantController {

    private MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @RequestMapping("/")
    @ResponseBody
    ResponseEntity home() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(
            value = "/mutant",
            consumes = "application/json")
    @ResponseBody
    ResponseEntity mutant(@RequestBody String body) {
        return mutantService.detectMutant(body);
    }

    @PostMapping(
            value = "/stats",
            produces = "application/json")
    @ResponseBody
    ResponseEntity stats() {
        return mutantService.stats();
    }

}
