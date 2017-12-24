package com.frontalini.mutantsmeli.controller;

import com.frontalini.mutantsmeli.model.Dna;
import com.frontalini.mutantsmeli.model.MutantModel;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class MutantController {

    public static void main(String[] args) {
		SpringApplication.run(MutantController.class, args);
	}

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping(
            value = "/mutant",
            method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    String mutant(@RequestBody String body) {
        Gson gson = new Gson();
        //TODO: Validar json
        Dna dna = gson.fromJson(body, Dna.class);
        MutantModel mutantModel = new MutantModel();
        return "Hello Mutant! " + mutantModel.isMutant(dna.getDna());
    }

}
