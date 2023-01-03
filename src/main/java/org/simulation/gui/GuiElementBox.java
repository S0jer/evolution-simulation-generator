package org.simulation.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.simulation.app.models.mapelement.MapElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private VBox vBox;

    public VBox getvBox() {
        return vBox;
    }

    public GuiElementBox(MapElement mapElement) {
        try {
            Image image = new Image(new FileInputStream(mapElement.getPathToImage()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            this.vBox = new VBox(imageView);
            this.vBox.setAlignment(Pos.CENTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
