package com.frontalini.mutantsmeli;

import com.frontalini.mutantsmeli.model.Stats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutantApplication.class)
public class StatsTests {

    @Autowired
    private Stats stats;

    @Test
    public void moreMutants() {
        stats.setCountMutant(3);
        stats.setCountHuman(2);
        assert stats.getRatio() == (3f / 2f);
    }

    @Test
    public void moreHumans() {
        stats.setCountMutant(3);
        stats.setCountHuman(5);
        assert stats.getRatio() == (3f / 5f);
    }

    @Test
    public void equalHumansMutants() {
        stats.setCountMutant(5);
        stats.setCountHuman(5);
        assert stats.getRatio() == 1;
    }

    @Test
    public void noMutants_noHumans() {
        stats.setCountMutant(0);
        stats.setCountHuman(0);
        assert stats.getRatio() == 0;
    }

    @Test
    public void noMutants() {
        stats.setCountMutant(0);
        stats.setCountHuman(10);
        assert stats.getRatio() == 0;
    }

    @Test
    public void noHumans() {
        stats.setCountMutant(10);
        stats.setCountHuman(0);
        assert stats.getRatio() == 1;
    }

}
