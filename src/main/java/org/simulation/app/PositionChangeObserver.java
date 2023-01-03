package org.simulation.app;

import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

public interface PositionChangeObserver {
    void positionChanged(MapElement mapElement, Vector2d oldPosition, Vector2d newPosition);
}
