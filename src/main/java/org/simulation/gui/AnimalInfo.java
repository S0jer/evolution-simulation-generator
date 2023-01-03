package org.simulation.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.simulation.app.models.mapelement.Animal;


public class AnimalInfo {
    private final Animal animal;
    private final VBox info;
    private final Label children;
    private final Label death;
    private final Label energy;
    private final Label activeGene;
    private final Label plantsEaten;

    public AnimalInfo(Animal animal) {
        Font basicFont = new Font("Arial", 12);
        this.animal = animal;
        Label title = new Label("ANIMAL INFO");
        title.setFont(new Font("Arial", 15));
//        GENOTYPE
        Label genotype = new Label(animal.getGenotype().toString());
        genotype.setFont(basicFont);
//        ACTIVE GENE
        activeGene = new Label(String.valueOf(animal.getGenotype().getCurrentGene()));
        activeGene.setFont(basicFont);
        Label activeGeneTitle = new Label("Active gene: ");
        activeGeneTitle.setFont(basicFont);
        HBox activeGeneBox = new HBox(activeGeneTitle, activeGene);
        activeGeneBox.setAlignment(Pos.CENTER);
//        NUMBER OF CHILDREN
        children = new Label(String.valueOf(animal.getChildren()));
        children.setFont(basicFont);
        Label childrenTitle = new Label("Number of Children: ");
        childrenTitle.setFont(basicFont);
        HBox childrenBox = new HBox(childrenTitle, children);
        childrenBox.setAlignment(Pos.CENTER);
//        ENERGY
        energy = new Label(String.valueOf(animal.getEnergy().getEnergyCount().intValue()));
        energy.setFont(basicFont);
        Label energyTitle = new Label("Energy: ");
        energyTitle.setFont(basicFont);
        HBox energyBox = new HBox(energyTitle, energy);
        energyBox.setAlignment(Pos.CENTER);
//        PLANTS EATEN
        plantsEaten = new Label(String.valueOf(animal.getPlantsEaten()));
        plantsEaten.setFont(basicFont);
        Label plantsEatenTitle = new Label("Plants eaten: ");
        plantsEatenTitle.setFont(basicFont);
        HBox plantsEatenBox = new HBox(plantsEatenTitle, plantsEaten);
        plantsEatenBox.setAlignment(Pos.CENTER);
//        LIFETIME\
        death = new Label(String.valueOf(animal.getLifetime()));
        Label lifetime = new Label("Alive for: ");
        death.setFont(basicFont);
        HBox deathBox = new HBox(lifetime, death);
        deathBox.setAlignment(Pos.CENTER);


        this.info = new VBox(title, genotype, activeGeneBox, energyBox, plantsEatenBox, childrenBox, deathBox);
        this.info.setAlignment(Pos.CENTER);
        this.info.setSpacing(7);
    }

    public void update() {
        children.setText(String.valueOf(animal.getChildren()));
        activeGene.setText(String.valueOf(animal.getGenotype().getCurrentGene()));
        energy.setText(String.valueOf(animal.getEnergy().getEnergyCount()));
        plantsEaten.setText(String.valueOf(animal.getPlantsEaten()));
        death.setText(String.valueOf(animal.getLifetime()));
        if (animal.getEnergy().isDead()) {
            death.setText(String.valueOf(animal.getLifetime()));
        }
    }

    public VBox getInfo() {
        return info;
    }
}
