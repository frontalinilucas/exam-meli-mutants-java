package com.frontalini.mutantsmeli.model;

public class MutantModel {

    private static final int QUANTITY_EQUAL_LETTERS = 4;
    private static final int QUANTITY_SEQUENCE = 2;
    private static final String VALID_LETTERS = "ATCG";

    public boolean isMutant(String[] dna){
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
