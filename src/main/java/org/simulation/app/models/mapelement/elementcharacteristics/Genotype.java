package org.simulation.app.models.mapelement.elementcharacteristics;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.List;

public class Genotype {
    private List<Gene> gens;
    private Integer currentGen;
    private final Integer genesSize;
    private final RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();

    public Genotype(List<Integer> gens) {
        this.gens = gens.stream().map(Gene::new).toList();
        this.currentGen = 0;
        this.genesSize = gens.size();
    }

    public Genotype(List<Integer> gens, boolean isRandomMutation) {
        this.gens = gens.stream().map(Gene::new).toList();
        mutation(isRandomMutation);
        this.currentGen = 0;
        this.genesSize = gens.size();
    }

    private void mutation(boolean isRandomMutation) {
        if (isRandomMutation) {
            this.gens = this.gens.stream().map(x -> {
                int mute = randomBehaviorGenerator.numberToGenerator(2);
                if (mute == 1) return new Gene(randomBehaviorGenerator.numberToGenerator(8));
                else return x;
            }).toList();
        } else {
            this.gens = this.gens.stream().map(x -> {
                int mute = randomBehaviorGenerator.numberToGenerator(2);
                if (mute == 1) {
                    if (randomBehaviorGenerator.numberToGenerator(2) == 1) {
                        return new Gene((x.getValue() + 1) % 8);
                    } else {
                        return new Gene(Math.abs(x.getValue() - 1) % 8);
                    }
                } else return x;
            }).toList();
        }
    }

    public Gene getCurrentGene() {
        Gene gene = gens.get((this.currentGen) % genesSize);
        if (EnvironmentVariables.isCrazyAnimals()) {
            int randomNumber = randomBehaviorGenerator.behaviorGenerator();
            if (randomNumber == 0 || randomNumber == 1) {
                this.currentGen = randomBehaviorGenerator.numberToGenerator(genesSize);
            } else {
                this.currentGen += 1;
            }
        } else {
            this.currentGen += 1;
        }
        return gene;
    }

    @Override
    public String toString() {
        return "Genotype: " + gens;
    }

    public List<Gene> getGens() {
        return gens;
    }
}
