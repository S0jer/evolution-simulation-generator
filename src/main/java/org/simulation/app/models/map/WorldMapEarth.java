package org.simulation.app.models.map;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

public class WorldMapEarth extends AbstractWorldMap {

    RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();

    public WorldMapEarth() {
        this.placeInitialAnimals();
        super.placeGrass();
    }

    @Override
    public Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition) {
        Vector2d properPosition = newPosition;
        if (properPosition.getY() > super.upperBorder.getY() || properPosition.getY() < super.bottomBorder.getY()) {
            properPosition = oldPosition;
        }

        if (newPosition.getX() > super.upperBorder.getX()) {
            properPosition = new Vector2d(super.bottomBorder.getX(), newPosition.getY());
        } else if (newPosition.getX() < super.bottomBorder.getX()) {
            properPosition = new Vector2d(super.upperBorder.getX(), newPosition.getY());
        }

        return properPosition;
    }


    private void placeInitialAnimals() {
        int startAnimals = EnvironmentVariables.getAnimalsQuantity();
        for (int i = 0; i < startAnimals; i++) {
            Vector2d pos = new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                    randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapHeight()));
            this.place(new Animal(this, pos, EnvironmentVariables.getAnimalEnergy()));
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}
