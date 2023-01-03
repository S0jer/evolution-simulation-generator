package org.simulation.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariable;
import org.simulation.app.models.mapelement.envvariables.EnvironmentVariables;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    private final Scene scene;
    private final BorderPane borderPane;
    private final Button startButton;
    private final Map<EnvironmentVariable, TextField> environmentVariables;

    public Menu() {
        this.borderPane = new BorderPane();
        this.environmentVariables = new HashMap<>();
        this.startButton = new Button("start");

        createMenu();
        this.scene = new Scene(borderPane, 1280, 720);
    }

    private void createMenu() {
//        EnvironmentVariables
        VBox environmentVars = new VBox(
                addParameter(EnvironmentVariable.MAP_WIDTH),
                addParameter(EnvironmentVariable.MAP_HEIGHT),
                addParameter(EnvironmentVariable.HELL),
                addParameter(EnvironmentVariable.PLANTS_QUANTITY),
                addParameter(EnvironmentVariable.PLANTS_ENERGY),
                addParameter(EnvironmentVariable.NEW_PLANTS_QUANTITY),
                addParameter(EnvironmentVariable.CORPSES),
                addParameter(EnvironmentVariable.ANIMALS_QUANTITY),
                addParameter(EnvironmentVariable.ANIMAL_ENERGY),
                addParameter(EnvironmentVariable.MIN_PROPAGATION_ENERGY),
                addParameter(EnvironmentVariable.PROPAGATION_LOSS),
                addParameter(EnvironmentVariable.RANDOM_MUTATION),
                addParameter(EnvironmentVariable.GENOME_SIZE),
                addParameter(EnvironmentVariable.CRAZY_ANIMALS),
                addParameter(EnvironmentVariable.SAVE_DATA)
        );
//        Title
        Label menuName = new Label("MENU");
        menuName.setFont(new Font("Arial", 20));
//
//        Start button
        HBox buttonBox = new HBox(startButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox menu = new VBox(
                menuName,
                environmentVars,
                buttonBox
        );
        menu.setSpacing(30);
        environmentVars.setSpacing(15);
        menu.setAlignment(Pos.CENTER);
        this.borderPane.setCenter(menu);
    }

    private HBox addParameter(EnvironmentVariable environmentVariable) {
        Label name = new Label(environmentVariable.toString());
        TextField input = new TextField();
        HBox hbox = new HBox(name, input);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        input.setText(EnvironmentVariables.getValueAsString(environmentVariable));
        environmentVariables.put(environmentVariable, input);
        return hbox;
    }

    public void submitInputs() {
        environmentVariables.forEach((key, value) -> {
            switch (key) {
                case MAP_WIDTH -> EnvironmentVariables.setMapWidth(Integer.parseInt(value.getText()));
                case MAP_HEIGHT -> EnvironmentVariables.setMapHeight(Integer.parseInt(value.getText()));
                case HELL -> EnvironmentVariables.setHELL(Boolean.parseBoolean(value.getText()));
                case PLANTS_QUANTITY -> EnvironmentVariables.setPlantsQuantity(Integer.parseInt(value.getText()));
                case PLANTS_ENERGY -> EnvironmentVariables.setPlantsEnergy(Integer.parseInt(value.getText()));
                case NEW_PLANTS_QUANTITY -> EnvironmentVariables.setNewPlantsQuantity(Integer.parseInt(value.getText()));
                case CORPSES -> EnvironmentVariables.setCORPSES(Boolean.parseBoolean(value.getText()));
                case ANIMALS_QUANTITY -> EnvironmentVariables.setAnimalsQuantity(Integer.parseInt(value.getText()));
                case ANIMAL_ENERGY -> EnvironmentVariables.setAnimalEnergy(Integer.parseInt(value.getText()));
                case MIN_PROPAGATION_ENERGY -> EnvironmentVariables.setMinPropagationEnergy(Integer.parseInt(value.getText()));
                case PROPAGATION_LOSS -> EnvironmentVariables.setPropagationLoss(Integer.parseInt(value.getText()));
                case RANDOM_MUTATION -> EnvironmentVariables.setRandomMutation(Boolean.parseBoolean(value.getText()));
                case GENOME_SIZE -> EnvironmentVariables.setGenomeSize(Integer.parseInt(value.getText()));
                case CRAZY_ANIMALS -> EnvironmentVariables.setCrazyAnimals(Boolean.parseBoolean(value.getText()));
                case SAVE_DATA -> EnvironmentVariables.setSaveData(Boolean.parseBoolean(value.getText()));
            }
        });
    }

    public Button getStartButton() {
        return startButton;
    }

    public Scene getScene() {
        return scene;
    }
}
