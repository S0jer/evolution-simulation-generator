package org.simulation.app.models.mapelement.elementcharacteristics;

import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

public class Energy {

    private Integer energyCount;

    public Energy(Integer energyCount) {
        this.energyCount = energyCount;
    }

    public Integer getEnergyCount() {
        return energyCount;
    }

    public void lose(Integer usedValue) {
        this.energyCount -= usedValue;
    }

    public Energy subtract(Energy other) {
        if (this.energyCount - other.getEnergyCount() < 0) return new Energy(0);
        return new Energy(this.energyCount - other.getEnergyCount());
    }

    public void gain() {
        energyCount += EnvironmentVariables.getPlantsEnergy();
    }

    public boolean isDead() {
        return this.energyCount > 0;
    }
}
