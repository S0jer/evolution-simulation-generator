package org.simulation.app.models.map;

import org.simulation.app.models.mapelement.Animal;

public class ChartDataProvider {
    AbstractWorldMap worldMap;

    public ChartDataProvider(AbstractWorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public int countEmpty() {
        return this.worldMap.getRightTopCorner().getX()* this.worldMap.getRightTopCorner().getY() -
                (this.worldMap.getAnimalsOnMap().size() + this.worldMap.getPlantsOnMap().size());
    }

    public int countAnimals() {
        return this.worldMap.getAnimalsOnMap().size();
    }

    public int countPlants() {
        return this.worldMap.getPlantsOnMap().size();
    }

    public int countAvgEnergy() {
        int counter = 0;
        int energy = 0;
        for (Animal animal : this.worldMap.getAnimalsOnMap()) {
            energy += animal.getEnergy().getEnergyCount();
            counter++;
        }
        if (counter > 0) {
            return energy / counter;
        } else return 0;
    }


    public int countAvgLifetime() {
        int counter = 0;
        int lifetime = 0;
        for (Animal animal : this.worldMap.getDeadAnimals()) {
            lifetime += animal.getLifetime();
            counter++;
        }
        if (counter > 0) {
            return lifetime / counter;
        } else {
            return 0;
        }
    }

    public int countAvgChildren() {
        int counter = 0;
        int children = 0;
        for (Animal animal : this.worldMap.getAnimalsOnMap()) {
            counter++;
            children += animal.getChildren();
        }
        if (counter > 0) {
            return children / counter;
        } else {
            return 0;
        }
    }
}
