package org.simulation.app.models.mapelement.envvariables;

public class EnvironmentVariables {
    private static int MAP_WIDTH = 10;
    private static int MAP_HEIGHT = 10;
    private static boolean HELL = false;
    private static int PLANTS_QUANTITY = 10;
    private static int PLANTS_ENERGY = 4;
    private static int NEW_PLANTS_QUANTITY = 5;
    private static boolean CORPSES = false;
    private static int ANIMALS_QUANTITY = 5;
    private static int ANIMAL_ENERGY = 15;
    private static int MIN_PROPAGATION_ENERGY = 7;
    private static int PROPAGATION_LOSS = 4;
    private static boolean RANDOM_MUTATION = false;
    private static int GENOME_SIZE = 32;
    private static boolean CRAZY_ANIMALS = false;

    public static void setMapWidth(int mapWidth) {
        MAP_WIDTH = mapWidth;
    }

    public static void setMapHeight(int mapHeight) {
        MAP_HEIGHT = mapHeight;
    }

    public static void setHELL(boolean HELL) {
        EnvironmentVariables.HELL = HELL;
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

    public static void setCORPSES(boolean CORPSES) {
        EnvironmentVariables.CORPSES = CORPSES;
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
}
