package com.frontalini.mutantsmeli;

import com.frontalini.mutantsmeli.model.MutantModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutantModel.class)
public class MutantModelTests {

    private MutantModel mutantModel;

    @Before
    public void setUpTest(){
        mutantModel = new MutantModel();
    }

    @Test
    public void firstExampleMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTTA", "TCACTG"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void secondExampleMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void noSequence() {
        String[] dna = {"ACGT", "TTGG", "AACA", "TGCG"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void onlyHorizontalSequence() {
        String[] dna = {"AAAA", "TTCG", "AGGC", "AGTT"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void onlyVerticalSequence() {
        String[] dna = {"AACC", "ATCG", "AGGC", "AGTT"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void onlyRightDiagonalSequence() {
        String[] dna = {"ACTG", "CATT", "TTAG", "GGGA"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void onlyLeftDiagonalSequence() {
        String[] dna = {"GTCA", "TTAC", "GATT", "AGGG"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void twoHorizontalSequence() {
        String[] dna = {"AAAA", "TTTT", "AGGC", "AGTT"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void twoVerticalSequence() {
        String[] dna = {"ATCC", "ATCG", "ATGC", "ATGT"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void twoDiagonalSequence() {
        String[] dna = {"ACTA", "CAAT", "TAAG", "AGGA"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void twoSequenceSameDirection() {
        String[] dna = {"ATCGT", "CACGA", "GTATA", "TGCAT", "ACTAA"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void lowerCaseLetters() {
        String[] dna = {"atcgta", "aaaaa", "ttttt", "tgcat", "actaa"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void invalidLetters() {
        String[] dna = {"QQQQQ", "HHHHH", "GTATA", "TGCAT", "ACTAA"};
        assert !mutantModel.isMutant(dna);
    }

    @Test
    public void borderSequence() {
        String[] dna = {"AAAA", "TCGA", "CGCA", "TGTA"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void smallMatrixMutant() {
        String[] dna = {"AAAA", "ATCG", "AGGC", "AGTT"};
        assert mutantModel.isMutant(dna);
    }

    @Test
    public void mediumMatrixMutant() {
        String[] dna = {"ATAATGCATGCATGCGCTAGCTTGCTAGCT", "CGTAGCAGTCGATCGATCGATCGATCGGCA", "AAGCTAGCATGCGAAGCTAGCTAGCTAGCA", "GCGCTTCGATCGACACATGTAGCTAGCTAG", "ATGCAGCTAGCTAGGCAGCTAGCTAGACAG",
                "TGCGAGCGATACGTCTAGCGATGCTAGCTG", "ACGTACGATCGATGCGCTCGAGAGATCGAT", "AGCTAGCTATAGCTAGAGCTAGCTCTAGCT", "GTGTCGATGCATGCTGATCGATCTCTAGCT", "AGTCTCTAGCTAGCTAGCTAGCTCTCTCGA",
                "GTACGATCGATCGATCGATCTCTAGTCGAG", "GAGAGATCGATCGATCGATCGATCGTAGCT", "TGTCTAGATCGATCGATCGATCGATCGATG", "GCGGTGTCTCGCTCGCTCGCTCGTGCTGCT", "TGCAGTCGAGCTAGCTAGCTAGCTAGCTAG",
                "CGATCGATCGATCGATCGATCGATCGATCG", "GTAGCTAGCTAGCTAGCTAGCTAGCTCTAG", "TGCATCGATCTCTAGTGTGATCTAGCTAGC", "GAGCGAGCGAGCGAGCGAGAGCGCGAGCGA", "ATCTACTCTCTATCTATCTACTCTCTATCT",
                "AGTAGTGTAGTGTAGATAGTGTAGATAGTG", "TGCTAGCTAGCTAGCTCGATCGATCGATCG", "AGTCGATCGCTAGCTAGCTCTCGATCGTAC", "ACGTATCGATCTAGCTAGCTAGCTCTAGCT", "GTCAGCTAGCTAGCTAGCTAGCTAGCTACG",
                "GATCGTAGCATGCTAGCTAGCTCTCTAGCT", "CAGTATCTCGATCGATCGATCGATCGATCG", "CGATCGATCGATCTCTAGCTAGCTAGCTAG", "CAGTCGATCGCTCTAGCTAGCTAGCTAGCT", "CGCTGATCGATCGATCGTCTCTAGCTAGCA"};
        assert mutantModel.isMutant(dna);
    }

}
