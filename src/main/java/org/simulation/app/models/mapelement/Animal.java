package org.simulation.app.models.mapelement;

import org.simulation.app.PositionChangeObserver;
import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.map.WorldMap;
import org.simulation.app.models.mapelement.elementcharacteristics.Energy;
import org.simulation.app.models.MapDirection;
import org.simulation.app.models.mapelement.elementcharacteristics.Gene;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.elementcharacteristics.Genotype;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Animal implements MapElement, Comparable<Animal> {

    private final WorldMap worldMap;
    private Vector2d animalPosition;
    private final Energy animalEnergy;
    private MapDirection animalMapDirection;
    private final Set<PositionChangeObserver> observers = new HashSet<>();
    private final RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();

    private Integer lifetime;

    private Integer children;

    private final Genotype genotype;


    public Animal(WorldMap worldMap, Vector2d animalPosition, Integer animalEnergy, MapDirection animalMapDirection, List<Integer> animalGens) {
        this.worldMap = worldMap;
        this.animalPosition = animalPosition;
        this.animalEnergy = new Energy(animalEnergy);
        this.animalMapDirection = animalMapDirection;
        this.genotype = new Genotype(animalGens, EnvironmentVariables.isRandomMutation());
        this.lifetime = 0;
        this.children = 0;
    }

    public Animal(WorldMap worldMap, Vector2d animalPosition, Integer animalEnergy) {
        List<Integer> animalGens = new ArrayList<>();
        for (int i = 0; i < EnvironmentVariables.getGenomeSize(); i++) {
            animalGens.add(randomBehaviorGenerator.numberToGenerator(8));
        }
        this.worldMap = worldMap;
        this.animalPosition = animalPosition;
        this.animalEnergy = new Energy(animalEnergy);
        this.animalMapDirection = MapDirection.values()[randomBehaviorGenerator.numberToGenerator(8)];
        this.genotype = new Genotype(animalGens);
        this.lifetime = 0;
        this.children = 0;
    }

    public void move() {
        this.changeDirection(this.genotype.getCurrentGene());
        Vector2d oldAnimalPosition = this.animalPosition;
        Vector2d newAnimalPosition = this.animalPosition.add(this.animalMapDirection.toUnitVector());
        Vector2d properPosition = this.worldMap.canMoveTo(oldAnimalPosition, newAnimalPosition);
        if (EnvironmentVariables.isHELL() && !newAnimalPosition.equals(properPosition))
            this.animalEnergy.lose(EnvironmentVariables.getMinPropagationEnergy());
        this.animalPosition = properPosition;
        positionChanged(oldAnimalPosition, properPosition);
    }


    public void eat() {
        this.animalEnergy.gain();
    }

    private void changeDirection(Gene gene) {
        for (int i = 0; i < gene.getValue(); i++) {
            this.animalMapDirection = this.animalMapDirection.next();
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.animalPosition;
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getChildren() {
        return children;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.observers.forEach(o -> o.positionChanged(this, oldPosition, newPosition));
    }

    public Energy getEnergy() {
        return this.animalEnergy;
    }


    public void addObserver(PositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(PositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    @Override
    public String getPathToImage() {
        if (this.animalEnergy.getEnergyCount() >= EnvironmentVariables.getAnimalEnergy()) {
            return "src/main/resources/animal-green.png";
        } else if (this.animalEnergy.getEnergyCount() >= EnvironmentVariables.getAnimalEnergy() / 2) {
            return "src/main/resources/animal-yellow.png";
        } else {
            return "src/main/resources/animal-red.png";
        }
    }

    public void incrementLifetime() {
        this.lifetime += 1;
    }

    public Animal breed(Animal secondParent) {
        Integer commonEnergy = this.animalEnergy.getEnergyCount() + secondParent.getEnergy().getEnergyCount();
        Integer firstParentP = this.animalEnergy.getEnergyCount() / commonEnergy;
        Integer secondParentP = secondParent.getEnergy().getEnergyCount() / commonEnergy;

        this.animalEnergy.lose(this.animalEnergy.getEnergyCount() * firstParentP);
        secondParent.getEnergy().lose(secondParent.getEnergy().getEnergyCount() * secondParentP);
        Integer childEnergy = this.animalEnergy.getEnergyCount() * firstParentP + secondParent.getEnergy().getEnergyCount() * secondParentP;
        int directId = this.randomBehaviorGenerator.numberToGenerator(8);
        MapDirection mapDirection = MapDirection.values()[directId];
        int getBorderPart = this.randomBehaviorGenerator.numberToGenerator(2);
        List<Gene> animalGens = new ArrayList<>();
        List<Integer> animalsGensValues;
        int getBorderPoint;
        if (getBorderPart == 0) {
            getBorderPoint = EnvironmentVariables.getGenomeSize() * firstParentP;

        } else {
            getBorderPoint = EnvironmentVariables.getGenomeSize() * secondParentP;
        }
        animalGens.addAll(this.genotype.getGens().subList(0, getBorderPoint + 1));
        animalGens.addAll(secondParent.getGenotype().getGens().subList(getBorderPoint + 1, EnvironmentVariables.getGenomeSize()));
        animalsGensValues = animalGens.stream().map(Gene::getValue).toList();

        return new Animal(this.worldMap, this.animalPosition, childEnergy, mapDirection, animalsGensValues);
    }

    @Override
    public int compareTo(Animal o) {
        int cp1 = this.animalEnergy.getEnergyCount().compareTo(o.animalEnergy.getEnergyCount());
        if (cp1 == 0) {
            int cp2 = this.lifetime.compareTo(o.lifetime);
            if (cp2 == 0) {
                return this.children.compareTo(o.children);
            }
            return cp2;
        }
        return cp1;
    }

    public void incrementChildren() {
        this.children += 1;
    }
}
