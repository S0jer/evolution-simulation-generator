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

import static java.lang.System.exit;


public class App extends Application implements DayFinishedObserver {
        private AbstractWorldMap leftMap;
        private AbstractWorldMap rightMap;
        private BorderPane mainPane;
        private GridPane leftMapGrid;
        private GridPane rightMapGrid;
        private Thread leftEngineThread;
        private Thread rightEngineThread;
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
                this.leftMapGrid = new GridPane();
                this.rightMapGrid = new GridPane();
                leftMapGrid.setGridLinesVisible(true);
                rightMapGrid.setGridLinesVisible(true);

            } catch (IllegalArgumentException e) {
                System.out.println(e);
                exit(0);
            }
        }

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setScene(menu.getScene());
            primaryStage.show();

            menu.getStartButton().setOnAction((event -> {
                this.menu.submitInputs();
                Scene scene = new Scene(this.mainPane, 1600, 900);
                createInterface();
                primaryStage.setScene(scene);
            }));
        }

        private void createInterface(){
//        MAPS
            this.leftMap = new WorldMapHell();
            this.rightMap = new WorldMapEarth();
            ThreadedSimulationEngine leftEngine = new ThreadedSimulationEngine(leftMap);
            leftEngine.addObserver(this);
            ThreadedSimulationEngine rightEngine = new ThreadedSimulationEngine(rightMap);
            rightEngine.addObserver(this);
            this.leftEngineThread = new Thread(leftEngine);
            this.rightEngineThread = new Thread(rightEngine);
            createAxes(leftMapGrid,leftMap);
            createAxes(rightMapGrid,rightMap);
            addObjects(leftMapGrid,leftMap);
            addObjects(rightMapGrid,rightMap);
//        THREAD
            this.leftEngineThread.start();
            this.rightEngineThread.start();
            this.leftMap.setRunning(true);
            this.rightMap.setRunning(true);
//        MAP INFO
            MapInfo leftMapInfo = new MapInfo(leftMap);
            MapInfo rightMapInfo = new MapInfo(rightMap);
            leftEngine.addObserver(leftMapInfo);
            rightEngine.addObserver(rightMapInfo);
//        CHART
            Chart leftChart = new Chart(this.leftMap);
            Chart rightChart = new Chart(this.rightMap);
            leftEngine.addObserver(leftChart);
            rightEngine.addObserver(rightChart);

            HBox charts = new HBox(new VBox(leftChart.getLineChart(),leftMapInfo.getMapInfo()) ,new VBox(rightChart.getLineChart(), rightMapInfo.getMapInfo()));
            this.mainPane.setCenter(charts);





//        STOP BUTTONS
            Button leftStopButton = new Button("stop");
            Button rightStopButton = new Button("stop");
            leftStopButton.setOnAction((event -> {
                if (leftMap.isRunning()){
                    this.leftEngineThread.suspend();
                    leftMap.setRunning(false);
                    leftStopButton.setText("start");
                }else{
                    this.leftEngineThread.resume();
                    leftMap.setRunning(true);
                    leftStopButton.setText("stop");
                }

            }));
            rightStopButton.setOnAction((event -> {
                if (rightMap.isRunning()){
                    this.rightEngineThread.suspend();
                    rightMap.setRunning(false);
                    rightStopButton.setText("start");
                }else{
                    this.rightEngineThread.resume();
                    rightMap.setRunning(true);
                    rightStopButton.setText("stop");
                }

            }));

            VBox leftVBox = new VBox(leftMapGrid, leftStopButton);
            VBox rightVBox = new VBox(rightMapGrid, rightStopButton);

            mainPane.setLeft(leftVBox);
            mainPane.setRight(rightVBox);

        }

        public void createAxes(GridPane grid, AbstractWorldMap map){
            grid.getColumnConstraints().clear();
            grid.getRowConstraints().clear();
            Integer minX = map.getLeftBottomCorner().getX();
            Integer minY = map.getLeftBottomCorner().getY();
            Integer maxX = map.getRightTopCorner().getX();
            Integer maxY = map.getRightTopCorner().getY();

            Label yx = new Label("y/x");
            grid.add(yx, 0, 0,1,1);
            GridPane.setHalignment(yx, HPos.CENTER);

            grid.getColumnConstraints().add(new ColumnConstraints(boxSize));
            grid.getRowConstraints().add(new RowConstraints(boxSize));
            for(Integer i = minX; i<= maxX;i++ ){
                Label label = new Label(i.toString());
                grid.add(label, i-minX+1, 0,1,1);
                grid.getColumnConstraints().add(new ColumnConstraints(boxSize));
                GridPane.setHalignment(label, HPos.CENTER);
            }
            for(Integer i = maxY; i >= minY;i--){
                Label label = new Label(i.toString());
                grid.add(label, 0, maxY-i+1,1,1);
                grid.getRowConstraints().add(new RowConstraints(boxSize));
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
        public void addObjects(GridPane grid, AbstractWorldMap map) {
            int minX = map.getLeftBottomCorner().getX();
            int minY = map.getLeftBottomCorner().getY();
            int maxX = map.getRightTopCorner().getX();
            int maxY = map.getRightTopCorner().getY();

            for(int x = minX; x<=maxX ; x++){
                for (int y = minY; y<=maxY ; y++){
                    Vector2d pos = new Vector2d(x,y);
                    if (map.isOccupied(pos)){
                        MapElement mapElement = (MapElement) map.objectAt(pos);
                        GuiElementBox guiElementBox = new GuiElementBox(mapElement);
                        if(mapElement instanceof Animal){
                            guiElementBox.getvBox().setOnMouseClicked((event -> {
                                if(!map.isRunning()){
                                    animalInfo = new AnimalInfo((Animal) mapElement);
                                    mainPane.setBottom(animalInfo.getInfo());
                                }
                            }));
                        }
                        grid.add(guiElementBox.getvBox(), x-minX+1, maxY+1-y,1,1);
                    }
                }
            }
        }

        @Override
        public void dayFinished() {
            Platform.runLater(() -> {
                mapUpdate(leftMapGrid, leftMap);
                mapUpdate(rightMapGrid, rightMap);
                if(animalInfo != null)animalInfo.update();
            });
        }
        private void mapUpdate(GridPane grid, AbstractWorldMap map){
            grid.getChildren().retainAll(grid.getChildren().get(0));
            createAxes(grid,map);
            addObjects(grid,map);
        }
}
