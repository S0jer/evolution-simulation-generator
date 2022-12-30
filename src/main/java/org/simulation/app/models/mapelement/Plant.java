package org.simulation.app.models.mapelement;

import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.map.Cemetery;
import org.simulation.app.models.map.WorldMap;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.Random;

public class Plant implements MapElement {
    private WorldMap worldMap;
    private Cemetery cemetery;
    private Vector2d plantPosition;
    private RandomBehaviorGenerator randomBehaviorGenerator;



    public Plant(WorldMap worldMap, Cemetery cemetery) {
        this.worldMap = worldMap;
        this.cemetery = cemetery;
        int poolChooseNumber = randomBehaviorGenerator.behaviorGenerator();
        if (EnvironmentVariables.isCORPSES()) {
            if (poolChooseNumber == 0 || poolChooseNumber == 1){
                cemetery.getOtherDeathsPositionsList();
            } else {
                cemetery.getOtherDeathsPositionsList();
            }


        } else {
            int mapHeight = EnvironmentVariables.getMapHeight();
            int bottomBorder = (int) ((mapHeight / 2) - mapHeight * 0.1);
            int upperBorder = (int) ((mapHeight / 2) + mapHeight * 0.1);
            if(poolChooseNumber == 0 || poolChooseNumber == 1) {
                this.plantPosition = new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                        randomBehaviorGenerator.numberExceptGenerator(bottomBorder, upperBorder, mapHeight));
            } else {
                this.plantPosition = new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                        randomBehaviorGenerator.numberToGenerator(upperBorder - bottomBorder) + bottomBorder);
            }
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.plantPosition;
    }
}
