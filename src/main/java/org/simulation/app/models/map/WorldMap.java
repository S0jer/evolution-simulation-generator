package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.elementcharacteristics.Genotype;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

import java.util.List;

public interface WorldMap {

    Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition);

    List<Animal> animalsAt(Vector2d position);

    void place(Animal mapElement);

    boolean isOccupied(Vector2d pos);

    Genotype countDominantGenome();
}
