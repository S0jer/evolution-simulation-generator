package org.simulation.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.simulation.app.DayFinishedObserver;
import org.simulation.app.models.map.AbstractWorldMap;

public class MapInfo implements DayFinishedObserver {
    private final AbstractWorldMap map;
    private final VBox mapInfo;
    private final Label dominantGenomeLabel;

    public MapInfo(AbstractWorldMap map) {
        this.map = map;
        this.dominantGenomeLabel = new Label(this.map.countDominantGenome().toString());
        this.mapInfo = new VBox(dominantGenomeLabel);
    }

    @Override
    public void dayFinished() {
        update();
    }

    private void update() {
        this.dominantGenomeLabel.setText(this.map.countDominantGenome().toString());
    }

    public VBox getMapInfo() {
        return mapInfo;
    }
}
