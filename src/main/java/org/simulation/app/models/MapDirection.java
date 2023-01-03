package org.simulation.app.models;

import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MapDirection {

    NORTH("N", new Vector2d(0, 1), new ArrayList<>(Arrays.asList("src/main/resources/animals/up-yellow.png", "src/main/resources/animals/up-orange.png", "src/main/resources/animals/up-red.png", "src/main/resources/animals/special.png"))),
    NORTHEAST("NE", new Vector2d(1, 1), new ArrayList<>(Arrays.asList("src/main/resources/animals/northeast-yellow.png", "src/main/resources/animals/northeast-orange.png", "src/main/resources/animals/northeast-red.png", "src/main/resources/animals/special.png"))),
    EAST("E", new Vector2d(1, 0), new ArrayList<>(Arrays.asList("src/main/resources/animals/right-yellow.png", "src/main/resources/animals/right-orange.png", "src/main/resources/animals/right-red.png", "src/main/resources/animals/special.png"))),
    SOUTHEAST("SE", new Vector2d(1, -1), new ArrayList<>(Arrays.asList("src/main/resources/animals/southeast-yellow.png", "src/main/resources/animals/southeast-orange.png", "src/main/resources/animals/southeast-red.png", "src/main/resources/animals/special.png"))),
    SOUTH("S", new Vector2d(0, -1), new ArrayList<>(Arrays.asList("src/main/resources/animals/down-yellow.png", "src/main/resources/animals/down-orange.png", "src/main/resources/animals/down-red.png", "src/main/resources/animals/special.png"))),
    SOUTHWEST("SW", new Vector2d(-1, -1), new ArrayList<>(Arrays.asList("src/main/resources/animals/southwest-yellow.png", "src/main/resources/animals/southwest-orange.png", "src/main/resources/animals/southwest-red.png", "src/main/resources/animals/special.png"))),
    WEST("W", new Vector2d(-1, 0), new ArrayList<>(Arrays.asList("src/main/resources/animals/left-yellow.png", "src/main/resources/animals/left-orange.png", "src/main/resources/animals/left-red.png", "src/main/resources/animals/special.png"))),
    NORTHWEST("NW", new Vector2d(-1, 1), new ArrayList<>(Arrays.asList("src/main/resources/animals/northwest-yellow.png", "src/main/resources/animals/northwest-orange.png", "src/main/resources/animals/northwest-red.png", "src/main/resources/animals/special.png")));


    private final String directionName;
    private final Vector2d unitVector;

    private final List<String> toImagesPaths;

    MapDirection(String directionName, Vector2d unitVector, List<String> toImagesPaths) {
        this.directionName = directionName;
        this.unitVector = unitVector;
        this.toImagesPaths = toImagesPaths;
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

    @Override
    public String toString() {
        return this.directionName;
    }

    public List<String> getToImagesPaths() {
        return toImagesPaths;
    }
}
