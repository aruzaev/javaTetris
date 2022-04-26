/*
 * Name: Tetris.java
Programmer: Artem Ruzaev
Purpose: The purpose of this program is to demonstrate all of the knowledge learned in the ICS4U course
Date: June 6, 2021
 */
package com.mycompany.javatetris; // calling the project package

// importing all of the necessary libraries
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Tetris extends JFrame { // setting up the class that will use JFrame

    JLabel statusbar; // creating a new JLabel called statusbarr
    Board board; // creating a new board

    public Tetris() { 

        statusbar = new JLabel(" 0"); // status bar initially sets the score to 0
        add(statusbar, BorderLayout.SOUTH); // adds the status bar at the bottom of the window

        
        board = new Board(this); // creates a new board
        add(board); // adds the board to the gui

        setSize(350, 550); // sets the size of the window
        setTitle("Tetris"); // sets the title of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE); // gives the ability to close the window
   }

   public JLabel getStatusBar() { // function that updates statusbar
       return statusbar;
   }

    public static void main(String[] args) { // main method

        Tetris game = new Tetris(); // creates a new instance of game
        game.setLocationRelativeTo(null); // centres the window on screen
        game.setVisible(true); // the window becomes visible

    }
}



