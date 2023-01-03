package org.simulation.app.models.mapelement;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.map.Cemetery;
import org.simulation.app.models.map.WorldMap;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.List;

public class Plant implements MapElement {
    private final WorldMap worldMap;
    private final Cemetery cemetery;
    private Vector2d plantPosition;
    private final RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();


    public Plant(WorldMap worldMap, Cemetery cemetery) {
        this.worldMap = worldMap;
        this.cemetery = cemetery;
        findPlantPosition(worldMap);
    }

    private void findPlantPosition(WorldMap worldMap) {
        boolean foundPosition = false;

        while (!foundPosition) {
            this.plantPosition = plantPlant();
            if (!worldMap.isOccupied(this.plantPosition)) {
                foundPosition = true;
            }
        }
    }

    private Vector2d plantPlant() {
        int poolChooseNumber = randomBehaviorGenerator.behaviorGenerator();
        if (EnvironmentVariables.isCORPSES()) {
            return corpsesPlanter(poolChooseNumber);
        } else {
            return defaultPlanter(poolChooseNumber);
        }
    }

    private Vector2d defaultPlanter(int poolChooseNumber) {
        int mapHeight = EnvironmentVariables.getMapHeight() + 1;
        int bottomBorder = (int) ((mapHeight / 2) - mapHeight * 0.1);
        int upperBorder = (int) ((mapHeight / 2) + mapHeight * 0.1);
        if (poolChooseNumber == 0 || poolChooseNumber == 1) {
            return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth() + 1),
                    randomBehaviorGenerator.numberExceptGenerator(bottomBorder, upperBorder, mapHeight));
        } else {
            return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth() + 1),
                    randomBehaviorGenerator.numberToGenerator(upperBorder - bottomBorder) + bottomBorder);
        }
    }

    private Vector2d corpsesPlanter(int poolChooseNumber) {
        List<Vector2d> numbersPool = this.cemetery.getOtherDeathsPositionsList();
        if (numbersPool != null && !numbersPool.isEmpty() && (poolChooseNumber == 0 || poolChooseNumber == 1)) {

            int randomIndex = randomBehaviorGenerator.numberToGenerator(numbersPool.size());
            return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth() + 1),
                    numbersPool.get(randomIndex).getY() + 1);
        } else {
            numbersPool = this.cemetery.getMinDeathsPositionsList();
            int randomIndex = randomBehaviorGenerator.numberToGenerator(numbersPool.size());
            return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth() + 1),
                    numbersPool.get(randomIndex).getY() + 1);
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.plantPosition;
    }

    @Override
    public String getPathToImage() {
        return "src/main/resources/grass.png";
    }
}
