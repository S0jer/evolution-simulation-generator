package org.simulation.app.models;

import org.simulation.app.models.mapelement.elementcharacteristics.Energy;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

public class EnergyCalculator {
    private static final Energy plantEnergy = new Energy(EnvironmentVariables.getPlantsEnergy());
    private static final Energy moveEnergy = new Energy(EnvironmentVariables.getMoveEnergy());
    private static final Energy startEnergy = new Energy(EnvironmentVariables.getAnimalEnergy());
    private static final Energy breedEnergy = new Energy(EnvironmentVariables.getMinPropagationEnergy());



    public static Energy calculateBreedEnergy(Energy currentEnergy){
        Integer calculatedBreedEnergy = currentEnergy.getEnergyCount()/4;
        return currentEnergy.subtract(new Energy(calculatedBreedEnergy));
    }
    public static boolean isTooLowOnEnergy(Energy currentEnergy){
        return currentEnergy.getEnergyCount() < moveEnergy.getEnergyCount();
    }
    public static boolean canBreed(Energy currentEnergy){
        return currentEnergy.getEnergyCount() > breedEnergy.getEnergyCount();
    }
}
