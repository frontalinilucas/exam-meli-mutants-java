package com.frontalini.mutantsmeli.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.stereotype.Component;

@Component
public class Stats {

    @SerializedName("count_mutant_dna")
    private int countMutant;

    @SerializedName("count_human_dna")
    private int countHuman;

    private float ratio;

    public int getCountMutant() {
        return countMutant;
    }

    public void setCountMutant(int countMutant) {
        this.countMutant = countMutant;
        calculateRatio();
    }

    public int getCountHuman() {
        return countHuman;
    }

    public void setCountHuman(int countHuman) {
        this.countHuman = countHuman;
        calculateRatio();
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    private void calculateRatio() {
        this.ratio = getCountHuman() == 0 && getCountMutant() == 0 ? 0 : (getCountHuman() == 0 ? 1 : (float) getCountMutant() / (float) getCountHuman());
    }
}
