/*
 * Name: MainMenu.java
Programmer: Artem Ruzaev
Date: June 6, 2021
 */

package com.mycompany.javatetris; // project package

// necessary libraries 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class MainMenu extends JPanel { // main menu 
    Label lab1; 
    JLabel lab2;
    private JPanel controlPanel; // panel called controlpanel

    public MainMenu(Board parent) { // initializes the main menu

        JPanel panel = new JPanel(); // creates new panel
        panel.setLayout(new GridLayout(3,1)); // sets layout
        lab1 = new Label("Tertis", Label.CENTER); // sets the title
        lab2 = new JLabel("<html>Controls:<br/><br/>" + // sets the instructions (NOTE: HTML tags are used to divide text into separate lines)
                "Left/Right Arrow Keys: Move the piece left and right<br/>" +
                "Down/Up Arrow Keys: Rotate the piece<br/>" +
                "D Key: Move the piece one line down<br/>" +
                "Spacebar: Drop the piece to the bottom<br/>" +
                "Esc Key: Pause the game</html>" +
                "</html>",
                Label.LEFT);
        

        controlPanel = new JPanel(); // creates new panel
        controlPanel.setBorder(new EmptyBorder(50,0,0,0)); // border for the panel
        Button playButton = new Button("Play"); // names the button
        playButton.addActionListener(e -> { // if the button is pressed then the game begins
            parent.startGame();
        });
        controlPanel.add(playButton); 
        lab1.setFont(new Font("Arial", Font.PLAIN, 20));

        panel.add(lab1, BorderLayout.NORTH);
        panel.add(lab2, BorderLayout.WEST);
        panel.add(controlPanel,  BorderLayout.SOUTH);
        add(panel);
    }
}
