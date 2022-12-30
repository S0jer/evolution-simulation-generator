package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

import java.util.List;

public interface WorldMap {

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place an worldMapElement on the map.
     *
     * @param mapElement The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(MapElement mapElement);

    /**
     * Return an object at a given position.
     *
     * @param position The position of the object.
     * @return Object or null if the position is not occupied.
     */
    MapElement objectAt(Vector2d position);

    List<Vector2d> getBorders();
}
