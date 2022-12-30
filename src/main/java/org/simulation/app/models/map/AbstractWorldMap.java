package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    private final Map<Vector2d, List<MapElement>> worldMap = new HashMap<>();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(MapElement mapElement) {
        List<MapElement> mapElements = this.worldMap.get(mapElement.getPosition());
        mapElements.add(mapElement);
        worldMap.put(mapElement.getPosition(), mapElements);
        return true;
    }

    public void positionChanged(MapElement mapElement, Vector2d oldPosition, Vector2d newPosition) {
        List<MapElement> mapElementsOld = worldMap.get(oldPosition);
        List<MapElement> mapElementsNew = worldMap.get(oldPosition);
        mapElementsOld.remove(mapElement);
        mapElementsNew.add(mapElement);

        worldMap.put(newPosition, mapElementsNew);
        worldMap.put(oldPosition, mapElementsOld);
    }

    @Override
    public List<Vector2d> getBorders() {
        return null;
    }
}
