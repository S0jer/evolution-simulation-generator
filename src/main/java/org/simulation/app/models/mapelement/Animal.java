package org.simulation.app.models.mapelement;

import org.simulation.app.PositionChangeObserver;
import org.simulation.app.models.map.WorldMap;
import org.simulation.app.models.mapelement.elementcharacteristics.Energy;
import org.simulation.app.models.MapDirection;
import org.simulation.app.models.mapelement.elementcharacteristics.Gene;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.elementcharacteristics.Genotype;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Animal implements MapElement {

    private final WorldMap worldMap;
    private Vector2d animalPosition;
    private Energy animalEnergy;
    private MapDirection animalMapDirection;
    private final Set<PositionChangeObserver> observers = new HashSet<>();

    private Genotype genotype;


    public Animal(WorldMap worldMap, Vector2d animalPosition, Integer animalEnergy, MapDirection animalMapDirection, List<Integer> animalGens, Integer animalCurrentGen) {
        this.worldMap = worldMap;
        this.animalPosition = animalPosition;
        this.animalEnergy = new Energy(animalEnergy);
        this.animalMapDirection = animalMapDirection;
        this.genotype = new Genotype(animalGens);
    }


    public void move() {
        this.changeDirection(this.genotype.getCurrentGene());
        Vector2d oldAnimalPosition = this.animalPosition;
        Vector2d newAnimalPosition = this.animalPosition.add(this.animalMapDirection.toUnitVector());
        this.animalPosition = newAnimalPosition;
        positionChanged(oldAnimalPosition, newAnimalPosition);
    }

    private void changeDirection(Gene gene) {
        for (int i = 0; i < gene.getValue(); i++){
            this.animalMapDirection = this.animalMapDirection.next();
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.animalPosition;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.observers.forEach(o -> o.positionChanged(this, oldPosition, newPosition));
    }


    public void addObserver(PositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(PositionChangeObserver observer) {
        this.observers.remove(observer);
    }
}
