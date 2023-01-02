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
    //    private final Label progeny;
    private final Label death;

    public AnimalInfo(Animal animal) {
        Font basicFont = new Font("Arial", 20);
        this.animal = animal;
        Label title = new Label("ANIMAL INFO");
        title.setFont(new Font("Arial", 30));
//        GENOM
        Label genom = new Label(animal.getGenotype().toString());
        genom.setFont(basicFont);
//        NUMBER OF CHILDRENS
        children = new Label(String.valueOf(animal.getChildren()));
        children.setFont(basicFont);
        Label childrenTitle = new Label("Number of Childrens: ");
        childrenTitle.setFont(basicFont);
        HBox childrenBox = new HBox(childrenTitle, children);
        childrenBox.setAlignment(Pos.CENTER);
//        NUMBER OF PROGENIES
//        progeny = new Label(String.valueOf(animal.countChildrenProgeny(new LinkedList<>())));
//        progeny.setFont(basicFont);
//        Label progenyTitle = new Label("Number of Progenies: ");
//        progenyTitle.setFont(basicFont);
//        HBox progenyBox = new HBox(progenyTitle, progeny);
//        progenyBox.setAlignment(Pos.CENTER);
//        LIFETIME
        death = new Label("alive");
        death.setFont(basicFont);


        this.info = new VBox(title, genom, childrenBox, death);
        this.info.setAlignment(Pos.CENTER);
        this.info.setSpacing(20);
    }

    public void update() {
        children.setText(String.valueOf(animal.getChildren()));
//        progeny.setText(String.valueOf(animal.countChildrenProgeny(new LinkedList<>())));
        if (animal.getEnergy().isDead()) {
            death.setText(String.valueOf(animal.getLifetime()));
        }
    }

    public VBox getInfo() {
        return info;
    }
}
