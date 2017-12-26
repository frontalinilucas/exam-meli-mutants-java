package com.frontalini.mutantsmeli.repositories;

import com.frontalini.mutantsmeli.model.Dna;
import com.frontalini.mutantsmeli.model.Stats;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;
import java.sql.SQLException;

@Repository
public interface GenericRepository {

    void saveDna(Dna dna) throws URISyntaxException, SQLException;
    Stats getStats() throws URISyntaxException, SQLException;

}
