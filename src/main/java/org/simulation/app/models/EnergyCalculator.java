package org.simulation.app.models;

import org.simulation.app.models.mapelement.elementcharacteristics.Energy;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

public class EnergyCalculator {
    private static final Energy moveEnergy = new Energy(EnvironmentVariables.getMoveEnergy());
    private static final Energy breedEnergy = new Energy(EnvironmentVariables.getMinPropagationEnergy());

    public static boolean canBreed(Energy currentEnergy) {
        return currentEnergy.getEnergyCount() > breedEnergy.getEnergyCount();
    }
}
