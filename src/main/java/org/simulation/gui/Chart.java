package org.simulation.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.simulation.app.CsvWriter;
import org.simulation.app.DayFinishedObserver;
import org.simulation.app.models.map.AbstractWorldMap;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.io.IOException;

public class Chart implements DayFinishedObserver {
    private final LineChart<Number, Number> lineChart;
    private final AbstractWorldMap map;
    private int daysCounter;
    private final CsvWriter csvWriter;
    private final XYChart.Series<Number, Number> animalSeries;
    private final XYChart.Series<Number, Number> grassSeries;
    private final XYChart.Series<Number, Number> emptySeries;
    private final XYChart.Series<Number, Number> avgEnergySeries;
    private final XYChart.Series<Number, Number> avgLifetimeSeries;
    private final XYChart.Series<Number, Number> avgChildrenSeries;

    public Chart(AbstractWorldMap map) {
        this.map = map;
        this.csvWriter = new CsvWriter();
        this.daysCounter = 0;
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("days");

        this.lineChart = new LineChart<>(xAxis, yAxis);

        this.animalSeries = new XYChart.Series<>();
        animalSeries.setName("Animals");
        this.grassSeries = new XYChart.Series<>();
        grassSeries.setName("Grasses");
        this.emptySeries = new XYChart.Series<>();
        emptySeries.setName("Empty");
        this.avgEnergySeries = new XYChart.Series<>();
        avgEnergySeries.setName("AVG Energy");
        this.avgLifetimeSeries = new XYChart.Series<>();
        avgLifetimeSeries.setName("AVG Lifetime");
        this.avgChildrenSeries = new XYChart.Series<>();
        avgChildrenSeries.setName("AVG Children");

        addSeries();
    }

    private void addSeries() {
        this.lineChart.getData().add(animalSeries);
        this.lineChart.getData().add(grassSeries);
        this.lineChart.getData().add(emptySeries);
        this.lineChart.getData().add(avgEnergySeries);
        this.lineChart.getData().add(avgLifetimeSeries);
        this.lineChart.getData().add(avgChildrenSeries);
    }

    @Override
    public void dayFinished() {
        update();
        daysCounter++;
    }

    private void update() {
        int countedAnimals = map.countAnimals();
        int countedPlants = map.countPlants();
        int countedEmpty = map.countEmpty();
        int countedAvgEnergy = map.countAvgEnergy();
        int countedAvgLifetime = map.countAvgLifetime();
        int countedAvgChildren = map.countAvgChildren();
        animalSeries.getData().add(new XYChart.Data<>(daysCounter, countedAnimals));
        grassSeries.getData().add(new XYChart.Data<>(daysCounter, countedPlants));
        emptySeries.getData().add(new XYChart.Data<>(daysCounter, countedEmpty));
        avgEnergySeries.getData().add(new XYChart.Data<>(daysCounter, countedAvgEnergy));
        avgLifetimeSeries.getData().add(new XYChart.Data<>(daysCounter, countedAvgLifetime));
        avgChildrenSeries.getData().add(new XYChart.Data<>(daysCounter, countedAvgChildren));
        if (EnvironmentVariables.isSaveData()) {
            csvWriter.createCsvDataSimple(countedAnimals, countedPlants, countedEmpty, countedAvgEnergy, countedAvgLifetime, countedAvgChildren);
        }

    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }
}
