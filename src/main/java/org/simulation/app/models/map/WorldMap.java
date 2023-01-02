package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.elementcharacteristics.Genotype;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

import java.util.List;

public interface WorldMap {

    Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition);


    List<MapElement> objectsAt(Vector2d position);

    List<Animal> animalsAt(Vector2d position);

    /**
     * Place an worldMapElement on the map.
     *
     * @param mapElement The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(Animal mapElement);

    boolean isOccupied(Vector2d pos);

    List<Vector2d> getBorders();

    Genotype countDominantGenome();
}
