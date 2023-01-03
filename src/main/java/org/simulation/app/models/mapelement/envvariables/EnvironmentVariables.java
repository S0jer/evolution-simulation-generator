package org.simulation.app.models.mapelement.envvariables;

public class EnvironmentVariables {
    private static int MAP_WIDTH = 20;
    private static int MAP_HEIGHT = 20;
    private static boolean HELL = false;
    private static int PLANTS_QUANTITY = 70;
    private static int PLANTS_ENERGY = 10;
    private static int NEW_PLANTS_QUANTITY = 10;
    private static boolean CORPSES = false;
    private static int ANIMALS_QUANTITY = 20;
    private static int ANIMAL_ENERGY = 20;
    private static int MIN_PROPAGATION_ENERGY = 7;
    private static int PROPAGATION_LOSS = 5;
    private static boolean RANDOM_MUTATION = true;
    private static int GENOME_SIZE = 32;
    private static boolean CRAZY_ANIMALS = true;

    private static int MOVE_ENERGY = 1;

    private static boolean SAVE_DATA = true;

    public static void setMapWidth(int mapWidth) {
        MAP_WIDTH = mapWidth;
    }

    public static void setMapHeight(int mapHeight) {
        MAP_HEIGHT = mapHeight;
    }

    public static void setHELL(boolean hell) {
        EnvironmentVariables.HELL = hell;
    }

    public static void setPlantsQuantity(int plantsQuantity) {
        PLANTS_QUANTITY = plantsQuantity;
    }

    public static void setPlantsEnergy(int plantsEnergy) {
        PLANTS_ENERGY = plantsEnergy;
    }

    public static void setNewPlantsQuantity(int newPlantsQuantity) {
        NEW_PLANTS_QUANTITY = newPlantsQuantity;
    }

    public static void setCORPSES(boolean corpses) {
        EnvironmentVariables.CORPSES = corpses;
    }

    public static void setAnimalsQuantity(int animalsQuantity) {
        ANIMALS_QUANTITY = animalsQuantity;
    }

    public static void setAnimalEnergy(int animalEnergy) {
        ANIMAL_ENERGY = animalEnergy;
    }

    public static void setMinPropagationEnergy(int minPropagationEnergy) {
        MIN_PROPAGATION_ENERGY = minPropagationEnergy;
    }

    public static void setPropagationLoss(int propagationLoss) {
        PROPAGATION_LOSS = propagationLoss;
    }

    public static void setRandomMutation(boolean randomMutation) {
        RANDOM_MUTATION = randomMutation;
    }

    public static void setGenomeSize(int genomeSize) {
        GENOME_SIZE = genomeSize;
    }

    public static void setCrazyAnimals(boolean crazyAnimals) {
        CRAZY_ANIMALS = crazyAnimals;
    }

    public static void setMoveEnergy(int moveEnergy) {
        MOVE_ENERGY = moveEnergy;
    }

    public static void setSaveData(boolean saveData) {
        SAVE_DATA = saveData;
    }

    public static int getMapWidth() {
        return MAP_WIDTH;
    }

    public static int getMapHeight() {
        return MAP_HEIGHT;
    }

    public static boolean isHELL() {
        return HELL;
    }

    public static int getPlantsQuantity() {
        return PLANTS_QUANTITY;
    }

    public static int getPlantsEnergy() {
        return PLANTS_ENERGY;
    }

    public static int getNewPlantsQuantity() {
        return NEW_PLANTS_QUANTITY;
    }

    public static boolean isCORPSES() {
        return CORPSES;
    }

    public static int getAnimalsQuantity() {
        return ANIMALS_QUANTITY;
    }

    public static int getAnimalEnergy() {
        return ANIMAL_ENERGY;
    }

    public static int getMinPropagationEnergy() {
        return MIN_PROPAGATION_ENERGY;
    }

    public static int getPropagationLoss() {
        return PROPAGATION_LOSS;
    }

    public static boolean isRandomMutation() {
        return RANDOM_MUTATION;
    }

    public static int getGenomeSize() {
        return GENOME_SIZE;
    }

    public static boolean isCrazyAnimals() {
        return CRAZY_ANIMALS;
    }

    public static int getMoveEnergy() {
        return MOVE_ENERGY;
    }

    public static boolean isSaveData() {
        return SAVE_DATA;
    }

    public static String getValueAsString(EnvironmentVariable environmentVariable) {
        switch (environmentVariable) {
            case MAP_WIDTH -> {
                return Integer.toString(getMapWidth());
            }
            case MAP_HEIGHT -> {
                return Integer.toString(getMapHeight());
            }
            case HELL -> {
                return Boolean.toString(isHELL());
            }
            case PLANTS_QUANTITY -> {
                return Integer.toString(getPlantsQuantity());
            }
            case PLANTS_ENERGY -> {
                return Integer.toString(getPlantsEnergy());
            }
            case NEW_PLANTS_QUANTITY -> {
                return Integer.toString(getNewPlantsQuantity());
            }
            case CORPSES -> {
                return Boolean.toString(isCORPSES());
            }
            case ANIMALS_QUANTITY -> {
                return Integer.toString(getAnimalsQuantity());
            }
            case ANIMAL_ENERGY -> {
                return Integer.toString(getAnimalEnergy());
            }
            case MIN_PROPAGATION_ENERGY -> {
                return Integer.toString(getMinPropagationEnergy());
            }
            case PROPAGATION_LOSS -> {
                return Integer.toString(getPropagationLoss());
            }
            case RANDOM_MUTATION -> {
                return Boolean.toString(isRandomMutation());
            }
            case GENOME_SIZE -> {
                return Integer.toString(getGenomeSize());
            }
            case CRAZY_ANIMALS -> {
                return Boolean.toString(isCrazyAnimals());
            }
            case SAVE_DATA -> {
                return Boolean.toString(isSaveData());
            }
            default -> throw new IllegalStateException("Unexpected value: " + environmentVariable);
        }
    }
}
