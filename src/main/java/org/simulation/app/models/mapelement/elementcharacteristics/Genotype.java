package org.simulation.app.models.mapelement.elementcharacteristics;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.List;
import java.util.stream.Collectors;

public class Genotype {
    private List<Gene> gens;
    private Integer currentGen;
    private Integer genesSize;
    private RandomBehaviorGenerator randomBehaviorGenerator;

    public Genotype(List<Integer> gens) {
        this.gens = gens.stream().map(Gene::new).collect(Collectors.toList());
        this.currentGen = 0;
        this.genesSize = gens.size();
    }


    public Gene getCurrentGene() {
        Gene gene = gens.get((this.currentGen) % genesSize);
        if (EnvironmentVariables.isCrazyAnimals()) {
            int randomNumber = randomBehaviorGenerator.behaviorGenerator();
            if (randomNumber == 0 || randomNumber == 1){
                this.currentGen =  randomBehaviorGenerator.numberToGenerator(genesSize);
            } else {
                this.currentGen += 1;
            }
        } else {
            this.currentGen += 1;
        }
        return gene;
    }
    public List<Gene> getGens() {
        return gens;
    }
}
