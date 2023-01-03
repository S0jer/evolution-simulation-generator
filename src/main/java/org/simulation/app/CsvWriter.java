package org.simulation.app;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {

    List<String[]> csvData;

    public CsvWriter() {
        csvData = new ArrayList<>();
        String[] header = {"Animals", "Plants", "Empty", "Avg Energy", "Avg  Lifetime", "Avg Children"};
        csvData.add(header);
    }

    private void saveCsvData() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(".\\simulationData.csv"))) {
            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCsvDataSimple(int countedAnimals, int countedPlants, int countedEmpty, int countedAvgEnergy, int countedAvgLifetime, int countedAvgChildren) {
        String[] record1 = {String.valueOf(countedAnimals), String.valueOf(countedPlants), String.valueOf(countedEmpty), String.valueOf(countedAvgEnergy), String.valueOf(countedAvgLifetime), String.valueOf(countedAvgChildren)};
        csvData.add(record1);
        saveCsvData();
    }
}
