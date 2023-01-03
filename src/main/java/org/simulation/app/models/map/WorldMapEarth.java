package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

public class WorldMapEarth extends AbstractWorldMap {

    public WorldMapEarth() {
        this.placeInitialAnimals();
        super.placeGrass(EnvironmentVariables.getPlantsQuantity());
    }

    @Override
    public Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition) {
        Vector2d properPosition = newPosition;
        properPosition = checkProperPositionVertically(oldPosition, properPosition);
        properPosition = checkProperPositionHorizontally(newPosition, properPosition);
        return properPosition;
    }

    private Vector2d checkProperPositionHorizontally(Vector2d newPosition, Vector2d properPosition) {
        if (newPosition.getX() > super.upperBorder.getX()) {
            properPosition = new Vector2d(super.bottomBorder.getX(), newPosition.getY());
        } else if (newPosition.getX() < super.bottomBorder.getX()) {
            properPosition = new Vector2d(super.upperBorder.getX(), newPosition.getY());
        }
        return properPosition;
    }

    private Vector2d checkProperPositionVertically(Vector2d oldPosition, Vector2d properPosition) {
        if (properPosition.getY() > super.upperBorder.getY() || properPosition.getY() < super.bottomBorder.getY()) {
            properPosition = oldPosition;
        }
        return properPosition;
    }
}
