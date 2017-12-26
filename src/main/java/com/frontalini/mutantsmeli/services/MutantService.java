package com.frontalini.mutantsmeli.services;

import com.frontalini.mutantsmeli.model.Dna;
import com.frontalini.mutantsmeli.repositories.GenericRepository;
import com.frontalini.mutantsmeli.repositories.MutantRepository;
import com.google.gson.Gson;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.sql.SQLException;

@Service
public class MutantService {

    private static final int QUANTITY_EQUAL_LETTERS = 4;
    private static final int QUANTITY_SEQUENCE = 2;
    private static final String VALID_LETTERS = "ATCG";

    private GenericRepository repository;

    @Autowired
    public MutantService(MutantRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity detectMutant(String body){
        try{
            Schema schema = SchemaLoader.load(new JSONObject(new JSONTokener(MutantService.class.getResourceAsStream("/mutant_schema.json"))));
            schema.validate(new JSONObject(new JSONTokener(body)));
        }catch (ValidationException e){
            return new ResponseEntity<>("Invalid body format.", HttpStatus.FORBIDDEN);
        }

        Dna dna = new Gson().fromJson(body, Dna.class);
        dna.setMutant(isMutant(dna.getDna()));
        try{
            repository.saveDna(dna);
        }catch(URISyntaxException | SQLException e){
            //TODO: Log error
        }

        return dna.isMutant() ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    public boolean isMutant(String[] dna) {
        int countDnaMutant = 0;
        for(int i = 0; i < dna.length; i++){
            for(int j = 0; j < dna.length; j++){
                if(isNeighbourEqual(dna, j, i, 1, 0, 1))
                    countDnaMutant++;
                if(isNeighbourEqual(dna, j, i, 1, 1, 1))
                    countDnaMutant++;
                if(isNeighbourEqual(dna, j, i, 0, 1, 1))
                    countDnaMutant++;
                if(isNeighbourEqual(dna, j, i, -1, 1, 1))
                    countDnaMutant++;
                if(countDnaMutant >= QUANTITY_SEQUENCE)
                    return true;
            }
        }
        return false;
    }

    private boolean isNeighbourEqual(String[] dna, int x, int y, int sumNeighbourX, int sumNeighbourY, int quantity) {
        int neighbourX = x + sumNeighbourX;
        int neighbourY = y + sumNeighbourY;
        if(neighbourX >= 0
                && neighbourY >= 0
                && neighbourX < dna.length
                && neighbourY < dna.length
                && dna[y].charAt(x) == dna[neighbourY].charAt(neighbourX)
                && VALID_LETTERS.contains(String.valueOf(dna[y].charAt(x)))) {
            quantity++;
            return quantity == QUANTITY_EQUAL_LETTERS || isNeighbourEqual(dna, neighbourX, neighbourY, sumNeighbourX, sumNeighbourY, quantity);
        }else
            return false;
    }

}
