package org.simulation.app.models.map;

import org.simulation.app.PositionChangeObserver;
import org.simulation.app.models.EnergyCalculator;
import org.simulation.app.models.RandomBehaviorGenerator;
import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.Plant;
import org.simulation.app.models.mapelement.elementcharacteristics.Genotype;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.*;

import static java.util.stream.Collectors.toCollection;

public abstract class AbstractWorldMap implements WorldMap, PositionChangeObserver {
    private final Map<Vector2d, List<MapElement>> worldMap = new HashMap<>();
    private final Cemetery cemetery = new Cemetery();
    private final Set<Animal> animalsOnMap = new HashSet<>();
    private final Set<Plant> plantsOnMap = new HashSet<>();
    private final Set<Animal> deadAnimals = new HashSet<>();
    private final ChartDataProvider chartDataProvider = new ChartDataProvider(this);
    private boolean isRunning;
    Vector2d upperBorder = new Vector2d(EnvironmentVariables.getMapWidth(), EnvironmentVariables.getMapHeight());
    Vector2d bottomBorder = new Vector2d(0, 0);
    private final RandomBehaviorGenerator randomBehaviorGenerator = new RandomBehaviorGenerator();


    @Override
    public boolean isOccupied(Vector2d pos) {
        if (worldMap.get(pos) == null) return false;
        return !worldMap.get(pos).isEmpty();
    }

    @Override
    public List<Animal> animalsAt(Vector2d position) {
        if (worldMap.get(position) != null) {
            return worldMap.get(position).stream().filter(Animal.class::isInstance).map(Animal.class::cast).toList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void place(Animal mapElement) {
        this.worldMap.computeIfAbsent(mapElement.getPosition(), k -> new ArrayList<>());
        animalsOnMap.add(mapElement);
        this.worldMap.get(mapElement.getPosition()).add(mapElement);
        mapElement.addObserver(this);
    }

    protected void placeInitialAnimals() {
        int startAnimals = EnvironmentVariables.getAnimalsQuantity();
        for (int i = 0; i < startAnimals; i++) {
            Vector2d pos = new Vector2d(randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapWidth()),
                    randomBehaviorGenerator.numberToGenerator(EnvironmentVariables.getMapHeight()));
            this.place(new Animal(this, pos, EnvironmentVariables.getAnimalEnergy()));
        }
    }

    @Override
    public void positionChanged(MapElement mapElement, Vector2d oldPosition, Vector2d newPosition) {
        List<MapElement> mapElementsOld = worldMap.get(oldPosition);
        this.worldMap.computeIfAbsent(newPosition, k -> new ArrayList<>());
        List<MapElement> mapElementsNew = worldMap.get(newPosition);
        if (mapElementsNew.stream().filter(Plant.class::isInstance).findFirst().isPresent()) {
            MapElement plant = mapElementsNew.stream().filter(Plant.class::isInstance).findFirst().get();
            if (plant instanceof Plant && mapElement instanceof Animal) {
                ((Animal) mapElement).eat();
                delete(plant);
            }
        }
        mapElementsOld.remove(mapElement);
        mapElementsNew.add(mapElement);
        worldMap.put(newPosition, mapElementsNew);
        worldMap.put(oldPosition, mapElementsOld);
    }


    public void deleteAnimals() {
        List<Animal> toDeleteList = new ArrayList<>();
        this.animalsOnMap.forEach(animal -> {
            if (animal.getEnergy().getEnergyCount() <= 0) {
                toDeleteList.add(animal);
            }
        });
        toDeleteList.forEach(animal -> {
            cemetery.animalDeath(animal.getPosition());
            delete(animal);
        });
    }


    private void delete(MapElement mapElement) {
        worldMap.get(mapElement.getPosition()).remove(mapElement);
        if (mapElement instanceof Animal) {
            animalsOnMap.remove(mapElement);
            deadAnimals.add((Animal) mapElement);
        } else if (mapElement instanceof Plant) {
            plantsOnMap.remove(mapElement);
        }
    }

    public void moveAnimals() {
        this.animalsOnMap.forEach(Animal::move);
    }

    public void animalsBreed() {
        int minx = this.bottomBorder.getX();
        int miny = this.bottomBorder.getY();
        int maxx = this.upperBorder.getX();
        int maxy = this.upperBorder.getY();
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                breedOnPosition(x, y);
            }
        }
    }

    private void breedOnPosition(int x, int y) {
        List<Animal> animalsOnPos = animalsAt(new Vector2d(x, y));
        if (animalsOnPos != null) {
            List<Animal> animalsOnPosForBreed = animalsForBreed(animalsOnPos);

            if (animalsOnPosForBreed == null || animalsOnPosForBreed.size() < 2) {
                return;
            }
            Collections.sort(animalsOnPosForBreed);
            Collections.reverse(animalsOnPosForBreed);

            getBestAndBreed(animalsOnPosForBreed);
        }
    }

    private void getBestAndBreed(List<Animal> animalsOnPosForBreed) {
        Animal firstParent;
        Animal secondParent;
        firstParent = animalsOnPosForBreed.get(0);
        secondParent = animalsOnPosForBreed.get(1);
        if (EnergyCalculator.canBreed(firstParent.getEnergy()) && EnergyCalculator.canBreed(secondParent.getEnergy())) {
            Animal child = firstParent.breed(secondParent);
            place(child);
            firstParent.incrementChildren();
            secondParent.incrementChildren();
        }
    }

    private List<Animal> animalsForBreed(List<Animal> animalsAt) {
        return animalsAt.stream().filter(x -> x.getEnergy().getEnergyCount() >= EnvironmentVariables.getMinPropagationEnergy()).collect(toCollection(ArrayList::new));
    }

    public void placeGrass(Integer plantsQuantity) {
        for (int i = 0; i < plantsQuantity; i++) {
            Plant plant = new Plant(this, cemetery);
            this.worldMap.computeIfAbsent(plant.getPosition(), k -> new ArrayList<>());
            worldMap.get(plant.getPosition()).add(plant);
            plantsOnMap.add(plant);
        }
    }

    public void incrementLifetime() {
        animalsOnMap.forEach(Animal::incrementLifetime);
    }

    @Override
    public Genotype countDominantGenome() {
        Map<Genotype, Integer> ranking = new HashMap<>();
        ranking.put(new Genotype(new ArrayList<>()), 1);
        for (Animal animal : this.animalsOnMap) {
            ranking.putIfAbsent(animal.getGenotype(), 1);
            ranking.put(animal.getGenotype(), ranking.get(animal.getGenotype()) + 1);
        }
        for (Animal animal : deadAnimals) {
            ranking.putIfAbsent(animal.getGenotype(), 1);
            ranking.put(animal.getGenotype(), ranking.get(animal.getGenotype()) + 1);
        }
        return Collections.max(ranking.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public MapElement objectAt(Vector2d position) {
        if (worldMap.get(position) != null && !worldMap.get(position).isEmpty()) {
            return worldMap.get(position).get(worldMap.get(position).size() - 1);
        } else {
            return null;
        }
    }

    public Vector2d getLeftBottomCorner() {
        return this.bottomBorder;
    }

    public Vector2d getRightTopCorner() {
        return this.upperBorder;
    }

    public ChartDataProvider getChartDataProvider() {
        return chartDataProvider;
    }

    public Set<Animal> getAnimalsOnMap() {
        return animalsOnMap;
    }

    public Set<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public Set<Plant> getPlantsOnMap() {
        return plantsOnMap;
    }
}
