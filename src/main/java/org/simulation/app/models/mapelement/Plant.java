package org.simulation.app.models.mapelement;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.map.Cemetery;
import org.simulation.app.models.map.WorldMap;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.List;

public class Plant implements MapElement {
    private WorldMap worldMap;
    private Cemetery cemetery;
    private Vector2d plantPosition;
    private final RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();


    public Plant(WorldMap worldMap, Cemetery cemetery) {
        this.worldMap = worldMap;
        this.cemetery = cemetery;
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
            List<Vector2d> numbersPool = this.cemetery.getOtherDeathsPositionsList();
            if (numbersPool != null && !numbersPool.isEmpty() && (poolChooseNumber == 0 || poolChooseNumber == 1)){

                int randomIndex = randomBehaviorGenerator.numberToGenerator(numbersPool.size());
                return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                        numbersPool.get(randomIndex).getY());
            } else {
                numbersPool = this.cemetery.getMinDeathsPositionsList();
                int randomIndex = randomBehaviorGenerator.numberToGenerator(numbersPool.size());
                return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                        numbersPool.get(randomIndex).getY());
            }
        } else {
            int mapHeight = EnvironmentVariables.getMapHeight();
            int bottomBorder = (int) ((mapHeight / 2) - mapHeight * 0.1);
            int upperBorder = (int) ((mapHeight / 2) + mapHeight * 0.1);
            if(poolChooseNumber == 0 || poolChooseNumber == 1) {
                return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                        randomBehaviorGenerator.numberExceptGenerator(bottomBorder, upperBorder, mapHeight));
            } else {
                return new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                        randomBehaviorGenerator.numberToGenerator(upperBorder - bottomBorder) + bottomBorder);
            }
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
