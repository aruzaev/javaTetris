/*
 * Name: GameOver.java
Programmer: Artem Ruzaev
Date: June 6, 2021
 */

package com.mycompany.javatetris; // project package

// necessary libraries
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameOver extends JPanel { // game over class
    public GameOver(Board parent) {
        JPanel panel = new JPanel(); // creates new panel
        
        // sets up panel attributes
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(4,1));
        panel.setBorder((new EmptyBorder(50,30,50,30)));
        Label lab = new Label("Game Over", Label.CENTER);
        lab.setFont(new Font("Arial", Font.PLAIN, 20));
        Label labScore = new Label("Current Score: " + parent.numLinesCleared, Label.CENTER);

        Button button1 = new Button("Replay"); // replay button
        button1.addActionListener(e -> { // if the button is pressed then the game restarts
            parent.reStart();
        });

        Button  button2 = new Button("Exit"); // exit button
        button2.addActionListener(e -> { // if the button is pressed then the program closes
            System.exit(1);
        });

        // adds all the elements to the GUI
        panel.add(lab);
        panel.add(labScore);
        panel.add(button1);
        panel.add(button2);

        add(panel, Integer.valueOf(100));
    }
}
