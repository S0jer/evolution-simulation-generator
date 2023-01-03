package org.simulation.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.simulation.app.DayFinishedObserver;
import org.simulation.app.ThreadedSimulationEngine;
import org.simulation.app.models.map.AbstractWorldMap;
import org.simulation.app.models.map.WorldMapEarth;
import org.simulation.app.models.map.WorldMapHell;
import org.simulation.app.models.mapelement.Animal;
import org.simulation.app.models.mapelement.MapElement;
import org.simulation.app.models.mapelement.elementcharacteristics.Vector2d;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;


import static java.lang.System.exit;


public class App extends Application implements DayFinishedObserver {
    private AbstractWorldMap map;
    private BorderPane mainPane;
    private GridPane mapGrid;
    private Thread engineThread;
    private final int boxSize = 30;
    private Menu menu;
    private AnimalInfo animalInfo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        try {
            this.menu = new Menu();
            this.mainPane = new BorderPane();
            this.mapGrid = new GridPane();
            mapGrid.setGridLinesVisible(true);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(menu.getScene());
        primaryStage.show();

        menu.getStartButton().setOnAction((event -> {
            runSimulation();
            primaryStage.close();
        }));
    }

    private void runSimulation() {
        Stage secondStage = new Stage();
        this.menu.submitInputs();
        Scene scene = new Scene(this.mainPane, 1600, 900);
        createInterface();
        secondStage.setScene(scene);
        secondStage.setOnCloseRequest(event -> {
            this.engineThread.stop();
        });
        secondStage.show();
    }

    private void createInterface() {
//        MAPS
        if (EnvironmentVariables.isHELL()) {
            this.map = new WorldMapHell();
        } else {
            this.map = new WorldMapEarth();
        }
        ThreadedSimulationEngine engine = new ThreadedSimulationEngine(map);
        engine.addObserver(this);
        this.engineThread = new Thread(engine);
        createAxes(mapGrid, map);
        addObjects(mapGrid, map);
//        THREAD
        this.engineThread.start();
        this.map.setRunning(true);
//        MAP INFO
        MapInfo mapInfo = new MapInfo(map);
        engine.addObserver(mapInfo);
//        CHART
        Chart chart = new Chart(this.map);
        engine.addObserver(chart);

        HBox charts = new HBox(new VBox(chart.getLineChart(), mapInfo.getMapInfo()));
        this.mainPane.setRight(charts);


//        STOP BUTTONS
        Button stopButton = createStopButton();

//        MARK DOMINANT GENE
        Button dominantGeneButton = createDominantGeneButton();

        VBox mainBox = new VBox(mapGrid, stopButton, dominantGeneButton);

        mainPane.setLeft(mainBox);
    }

    private Button createDominantGeneButton() {
        Button dominantGeneButton = new Button("Mark dominant");
        dominantGeneButton.setOnAction((event -> {
            if (!map.isRunning()) {
                map.getAnimalsOnMap().forEach(animal -> {
                    if (animal.getGenotype().getGens().equals(map.countDominantGenome().getGens()))
                        animal.setMarked(!animal.isMarked());
                });
            }
        }));
        return dominantGeneButton;
    }

    private Button createStopButton() {
        Button stopButton = new Button("stop");
        stopButton.setOnAction((event -> {
            if (map.isRunning()) {
                this.engineThread.suspend();
                map.setRunning(false);
                stopButton.setText("start");
            } else {
                this.engineThread.resume();
                map.setRunning(true);
                stopButton.setText("stop");
            }

        }));
        return stopButton;
    }

    public void createAxes(GridPane grid, AbstractWorldMap map) {
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        Integer minX = map.getLeftBottomCorner().getX();
        int minY = map.getLeftBottomCorner().getY();
        int maxX = map.getRightTopCorner().getX();
        Integer maxY = map.getRightTopCorner().getY();

        Label yx = new Label("y/x");
        grid.add(yx, 0, 0, 1, 1);
        GridPane.setHalignment(yx, HPos.CENTER);

        grid.getColumnConstraints().add(new ColumnConstraints(boxSize));
        grid.getRowConstraints().add(new RowConstraints(boxSize));
        setColumns(grid, minX, maxX);
        setRows(grid, minY, maxY);
    }

    private void setRows(GridPane grid, int minY, Integer maxY) {
        for (Integer i = maxY; i >= minY; i--) {
            Label label = new Label(i.toString());
            grid.add(label, 0, maxY - i + 1, 1, 1);
            grid.getRowConstraints().add(new RowConstraints(boxSize));
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void setColumns(GridPane grid, Integer minX, int maxX) {
        for (Integer i = minX; i <= maxX; i++) {
            Label label = new Label(i.toString());
            grid.add(label, i - minX + 1, 0, 1, 1);
            grid.getColumnConstraints().add(new ColumnConstraints(boxSize));
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    public void addObjects(GridPane grid, AbstractWorldMap map) {
        int minX = map.getLeftBottomCorner().getX();
        int minY = map.getLeftBottomCorner().getY();
        int maxX = map.getRightTopCorner().getX();
        int maxY = map.getRightTopCorner().getY();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Vector2d pos = new Vector2d(x, y);
                if (map.isOccupied(pos)) {
                    addElementToGrid(grid, map, minX, maxY, x, y, pos);
                }
            }
        }
    }

    private void addElementToGrid(GridPane grid, AbstractWorldMap map, int minX, int maxY, int x, int y, Vector2d pos) {
        MapElement mapElement = map.objectAt(pos);
        GuiElementBox guiElementBox = new GuiElementBox(mapElement);
        if (mapElement instanceof Animal) {
            guiElementBox.getvBox().setOnMouseClicked((event -> {
                if (!map.isRunning()) {
                    animalInfo = new AnimalInfo((Animal) mapElement);
                    mainPane.setBottom(animalInfo.getInfo());
                }
            }));
        }
        grid.add(guiElementBox.getvBox(), x - minX + 1, maxY + 1 - y, 1, 1);
    }

    @Override
    public void dayFinished() {
        Platform.runLater(() -> {
            mapUpdate(mapGrid, map);
            if (animalInfo != null) animalInfo.update();
        });
    }

    private void mapUpdate(GridPane grid, AbstractWorldMap map) {
        grid.getChildren().retainAll(grid.getChildren().get(0));
        createAxes(grid, map);
        addObjects(grid, map);
    }
}
