package org.simulation.app.models.map;

import org.simulation.app.PositionChangeObserver;
import org.simulation.app.models.EnergyCalculator;
import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.Plant;
import org.simulation.app.models.mapelement.elementcharacteristics.Genotype;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap, PositionChangeObserver {
    private final Map<Vector2d, List<MapElement>> worldMap = new HashMap<>();
    private final Cemetery cemetery = new Cemetery();
    private final Set<Animal> animalsOnMap = new HashSet<>();
    private final Set<Plant> plantsOnMap = new HashSet<>();
    private final Set<Animal> deadAnimals = new HashSet<>();
    private boolean isRunning;
    Vector2d upperBorder = new Vector2d(EnvironmentVariables.getMapWidth(), EnvironmentVariables.getMapHeight());
    Vector2d bottomBorder = new Vector2d(0, 0);


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
    public List<MapElement> objectsAt(Vector2d position) {
        return worldMap.get(position);
    }

    @Override
    public void place(Animal mapElement) {
        this.worldMap.computeIfAbsent(mapElement.getPosition(), k -> new ArrayList<>());
        animalsOnMap.add(mapElement);
        this.worldMap.get(mapElement.getPosition()).add(mapElement);
        mapElement.addObserver(this);
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
        Animal firstParent, secondParent;
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                List<Animal> animalsOnPos = animalsAt(new Vector2d(x, y));
                if (animalsOnPos != null) {
                    List<Animal> animalsOnPosForBreed = animalsForBreed(animalsOnPos);

                    if (animalsOnPosForBreed == null || animalsOnPosForBreed.size() < 2) {
                        continue;
                    }
                    Collections.sort(animalsOnPosForBreed);
                    Collections.reverse(animalsOnPosForBreed);

                    firstParent = animalsOnPosForBreed.get(0);
                    secondParent = animalsOnPosForBreed.get(1);
                    if (EnergyCalculator.canBreed(firstParent.getEnergy()) && EnergyCalculator.canBreed(secondParent.getEnergy())) {
                        Animal child = firstParent.breed(secondParent);
                        place(child);
                        firstParent.incrementChildren();
                        secondParent.incrementChildren();
                    }
                }
            }
        }


    }

    private List<Animal> animalsForBreed(List<Animal> animalsAt) {
        return animalsAt.stream().filter(x -> x.getEnergy().getEnergyCount() >= EnvironmentVariables.getMinPropagationEnergy()).collect(Collectors.toList());
    }

    public void placeGrass() {
        for (int i = 0; i < EnvironmentVariables.getNewPlantsQuantity(); i++) {
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


    public int countAnimals() {
        return this.animalsOnMap.size();
    }

    public int countPlants() {
        return plantsOnMap.size();
    }

    public int countAvgEnergy() {
        int counter = 0;
        int energy = 0;
        for (Animal animal : this.animalsOnMap) {
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
        for (Animal animal : deadAnimals) {
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
        for (Animal animal : this.animalsOnMap) {
            counter++;
            children += animal.getChildren();
        }
        if (counter > 0) {
            return children / counter;
        } else {
            return 0;
        }
    }

    public Vector2d getLeftBottomCorner() {
        return this.bottomBorder;
    }

    public Vector2d getRightTopCorner() {
        return this.upperBorder;
    }
}
