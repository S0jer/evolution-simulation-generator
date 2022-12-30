package org.simulation.app.models;

import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

public enum MapDirection {

    NORTH("N", new Vector2d(0, 1), "up.png"),
    NORTHEAST("NE", new Vector2d(1, 1), "up.png"),
    EAST("E", new Vector2d(1, 0), "right.png"),
    SOUTHEAST("SE", new Vector2d(1, -1), "up.png"),
    SOUTH("S", new Vector2d(0, -1), "down.png"),
    SOUTHWEST("SW", new Vector2d(-1, -1), "up.png"),
    WEST("W", new Vector2d(-1, 0), "left.png"),
    NORTHWEST("NW", new Vector2d(-1, 1), "up.png");


    private final String directionName;
    private final Vector2d unitVector;

    private final String viewName;

    MapDirection(String directionName, Vector2d unitVector, String viewName) {
        this.directionName = directionName;
        this.unitVector = unitVector;
        this.viewName = viewName;
    }

    public MapDirection next() {
        return MapDirection.values()[(this.ordinal() + 1) % 8];
    }

    public MapDirection previous() {
        return this.ordinal() - 1 >= 0 ? MapDirection.values()[(this.ordinal() - 1) % 8] : MapDirection.values()[3];
    }


    public Vector2d toUnitVector() {
        return this.unitVector;
    }

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public String toString() {
        return this.directionName;
    }
}
