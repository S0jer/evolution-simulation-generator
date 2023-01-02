package org.simulation.app.models.mapelement;

import org.simulation.app.PositionChangeObserver;
import org.simulation.app.models.map.AbstractWorldMap;
import org.simulation.app.models.map.WorldMap;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

public interface MapElement {
    Vector2d getPosition();

    String getPathToImage();
}
