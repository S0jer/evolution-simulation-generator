package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cemetery {
    private final Map<Vector2d, Integer> cemeteryMap = new HashMap<>();
    Integer width = EnvironmentVariables.getMapWidth();
    Integer height = EnvironmentVariables.getMapHeight();
    Integer minDeathsCounter;
    Integer minDeathsValue = 0;


    public Cemetery() {
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) {
                cemeteryMap.put(new Vector2d(j, i), 0);
            }
        }
        this.minDeathsCounter = cemeteryMap.keySet().size();
    }

    public List<Vector2d> getMinDeathsPositionsList() {
        return cemeteryMap.keySet().stream().filter(x -> cemeteryMap.get(x).equals(minDeathsValue)).toList();
    }

    public List<Vector2d> getOtherDeathsPositionsList() {
        return cemeteryMap.keySet().stream().filter(x -> !cemeteryMap.get(x).equals(minDeathsValue)).toList();
    }

    public void animalDeath(Vector2d gravePosition) {
        Integer deathsAtGravePosition = cemeteryMap.get(gravePosition);
        if (deathsAtGravePosition != null && deathsAtGravePosition.equals(minDeathsValue)) {
            minDeathsCounter -= 1;
            updateMinDeaths();
        }
        deathsAtGravePosition += 1;
        cemeteryMap.put(gravePosition, deathsAtGravePosition);
    }


    private void updateMinDeaths() {
        if (minDeathsCounter <= 0) {
            minDeathsValue += 1;
            minDeathsCounter = cemeteryMap.values().stream().filter(x -> x.equals(minDeathsValue)).toList().size();
        }
    }
}
