package org.simulation.app.models.map;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

public class WorldMapHell extends AbstractWorldMap {

    RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();

    public WorldMapHell() {
        this.placeInitialAnimals();
        super.placeGrass();
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
    public Vector2d canMoveTo(Vector2d oldPosition, Vector2d newPosition) {
        Vector2d properPosition = newPosition;
        if (properPosition.getY() > super.upperBorder.getY() || properPosition.getY() < super.bottomBorder.getY()
                || newPosition.getX() > super.upperBorder.getX() || newPosition.getX() < super.bottomBorder.getX()) {
            properPosition = new Vector2d(randomBehaviorGenerator.numberToGenerator(super.upperBorder.getX()),
                    randomBehaviorGenerator.numberToGenerator(super.upperBorder.getY()));
        }
        return properPosition;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}
