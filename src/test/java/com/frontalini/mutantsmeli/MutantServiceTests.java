package com.frontalini.mutantsmeli;

import com.frontalini.mutantsmeli.exceptions.InvalidDimensionMatrixException;
import com.frontalini.mutantsmeli.services.MutantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutantApplication.class)
public class MutantServiceTests {

    @Autowired
    private MutantService mutantService;

    @Test
    public void firstExampleMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTTA", "TCACTG"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void secondExampleMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assert mutantService.isMutant(dna);
    }

    @Test
    public void noSequence() {
        String[] dna = {"ACGT", "TTGG", "AACA", "TGCG"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void onlyHorizontalSequence() {
        String[] dna = {"AAAA", "TTCG", "AGGC", "AGTT"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void onlyVerticalSequence() {
        String[] dna = {"AACC", "ATCG", "AGGC", "AGTT"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void onlyRightDiagonalSequence() {
        String[] dna = {"ACTG", "CATT", "TTAG", "GGGA"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void onlyLeftDiagonalSequence() {
        String[] dna = {"GTCA", "TTAC", "GATT", "AGGG"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void twoHorizontalSequence() {
        String[] dna = {"AAAA", "TTTT", "AGGC", "AGTT"};
        assert mutantService.isMutant(dna);
    }

    @Test
    public void twoVerticalSequence() {
        String[] dna = {"ATCC", "ATCG", "ATGC", "ATGT"};
        assert mutantService.isMutant(dna);
    }

    @Test
    public void twoDiagonalSequence() {
        String[] dna = {"ACTA", "CAAT", "TAAG", "AGGA"};
        assert mutantService.isMutant(dna);
    }

    @Test
    public void twoSequenceSameDirection() {
        String[] dna = {"ATCGT", "CACGA", "GTATA", "TGCAT", "ACTAA"};
        assert mutantService.isMutant(dna);
    }

    @Test
    public void lowerCaseLetters() {
        String[] dna = {"atcgta", "aaaaa", "ttttt", "tgcat", "actaa"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void invalidLetters() {
        String[] dna = {"QQQQQ", "HHHHH", "GTATA", "TGCAT", "ACTAA"};
        assert !mutantService.isMutant(dna);
    }

    @Test
    public void borderSequence() {
        String[] dna = {"AAAA", "TCGA", "CGCA", "TGTA"};
        assert mutantService.isMutant(dna);
    }

    @Test(expected = InvalidDimensionMatrixException.class)
    public void invalidMatrixDimension() {
        String[] dna = {"AAA", "ATC", "AGG", "AGT"};
        mutantService.isMutant(dna);
    }

    @Test
    public void smallMatrixMutant() {
        String[] dna = {"AAAA", "ATCG", "AGGC", "AGTT"};
        assert mutantService.isMutant(dna);
    }

    @Test
    public void mediumMatrixMutant() {
        String[] dna = {"ATAATGCATGCATGCGCTAGCTTGCTAGCT", "CGTAGCAGTCGATCGATCGATCGATCGGCA", "AAGCTAGCATGCGAAGCTAGCTAGCTAGCA", "GCGCTTCGATCGACACATGTAGCTAGCTAG", "ATGCAGCTAGCTAGGCAGCTAGCTAGACAG",
                "TGCGAGCGATACGTCTAGCGATGCTAGCTG", "ACGTACGATCGATGCGCTCGAGAGATCGAT", "AGCTAGCTATAGCTAGAGCTAGCTCTAGCT", "GTGTCGATGCATGCTGATCGATCTCTAGCT", "AGTCTCTAGCTAGCTAGCTAGCTCTCTCGA",
                "GTACGATCGATCGATCGATCTCTAGTCGAG", "GAGAGATCGATCGATCGATCGATCGTAGCT", "TGTCTAGATCGATCGATCGATCGATCGATG", "GCGGTGTCTCGCTCGCTCGCTCGTGCTGCT", "TGCAGTCGAGCTAGCTAGCTAGCTAGCTAG",
                "CGATCGATCGATCGATCGATCGATCGATCG", "GTAGCTAGCTAGCTAGCTAGCTAGCTCTAG", "TGCATCGATCTCTAGTGTGATCTAGCTAGC", "GAGCGAGCGAGCGAGCGAGAGCGCGAGCGA", "ATCTACTCTCTATCTATCTACTCTCTATCT",
                "AGTAGTGTAGTGTAGATAGTGTAGATAGTG", "TGCTAGCTAGCTAGCTCGATCGATCGATCG", "AGTCGATCGCTAGCTAGCTCTCGATCGTAC", "ACGTATCGATCTAGCTAGCTAGCTCTAGCT", "GTCAGCTAGCTAGCTAGCTAGCTAGCTACG",
                "GATCGTAGCATGCTAGCTAGCTCTCTAGCT", "CAGTATCTCGATCGATCGATCGATCGATCG", "CGATCGATCGATCTCTAGCTAGCTAGCTAG", "CAGTCGATCGCTCTAGCTAGCTAGCTAGCT", "CGCTGATCGATCGATCGTCTCTAGCTAGCA"};
        assert mutantService.isMutant(dna);
    }

}
