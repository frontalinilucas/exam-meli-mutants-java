package com.frontalini.mutantsmeli.model;

import java.util.Arrays;

public class Dna {

    private String[] dna;
    private boolean isMutant;

    public String[] getDna() {
        return dna;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public int getMutant(){
        return isMutant ? 1 : 0;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }

    @Override
    public String toString(){
        return Arrays.toString(dna);
    }
}
