package org.simulation.app;

import org.simulation.app.models.map.AbstractWorldMap;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThreadedSimulationEngine implements Engine, Runnable {
    private final AbstractWorldMap map;
    private final List<DayFinishedObserver> observers = new ArrayList<>();
    private final int moveDelay;
    private final int newPlants;

    public ThreadedSimulationEngine(AbstractWorldMap map) {
        this.map = map;
        this.moveDelay = 500;
        this.newPlants = EnvironmentVariables.getNewPlantsQuantity();
    }

    @Override
    public void run() {
        while (true) {
            this.map.deleteAnimals();
            this.map.moveAnimals();
            this.map.animalsBreed();
            this.map.placeGrass(newPlants);
            this.map.incrementLifetime();
            dayFinished();
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addObserver(DayFinishedObserver observer) {
        observers.add(observer);
    }

    public void dayFinished() {
        observers.forEach(dayFinishedObserver -> {
            try {
                dayFinishedObserver.dayFinished();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
