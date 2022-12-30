package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cemetery {

    private final Map<Vector2d, Integer> cemetery = new HashMap<>();
    Integer width = EnvironmentVariables.getMapWidth();
    Integer height = EnvironmentVariables.getMapHeight();
    Integer minDeathsCounter;
    Integer minDeathsValue = 0;


    public Cemetery() {
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
               cemetery.put(new Vector2d(i, j), 0);
            }
        }
        this.minDeathsCounter = cemetery.keySet().size();
    }

    public List<Integer> getMinDeathsPositionsList() {
        return cemetery.values().stream().filter(x -> x.equals(minDeathsValue)).toList();
    }

    public List<Integer> getOtherDeathsPositionsList() {
        return cemetery.values().stream().filter(x -> !x.equals(minDeathsValue)).toList();
    }

    public void animalDeath(Vector2d gravePosition){
        Integer deathsAtGravePosition = cemetery.get(gravePosition);
        if(deathsAtGravePosition.equals(minDeathsValue)){
            minDeathsCounter -= 1;
            updateMinDeaths();
        }
        deathsAtGravePosition += 1;
        cemetery.put(gravePosition, deathsAtGravePosition);
    }


    private void updateMinDeaths() {
        if (minDeathsCounter <= 0){
            minDeathsValue += 1;
            minDeathsCounter = cemetery.values().stream().filter(x -> x.equals(minDeathsValue)).toList().size();
        }
    }
}
