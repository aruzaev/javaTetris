/*
 * Name: Tetris.java
Programmer: Artem Ruzaev
Date: June 6, 2021
 */

// sets up all of the libraries necessary to make the board functional
package com.mycompany.javatetris; // project package

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.mycompany.javatetris.Shape.Tetrominoes; // imports the pieces from project package


public class Board extends JPanel implements ActionListener { // board class


    // sets up dimensions of the board
    final int BoardWidth = 10;
    final int BoardHeight = 22;

    Timer timer; // sets a timer
    boolean isFallingFinished = false; // sees if the piece is falling
    boolean isStarted = false; // sees if the game has started
    boolean isPaused = false; // sees if the game is paused
    int numLinesCleared = 0; // counter for how many lines were cleared
    // tracks the current coords
    int currentX = 0;
    int currentY = 0;
    // initializing gui components
    JLabel statusbar;
    Shape currentPiece;
    Tetrominoes[] board;
    MainMenu menu;
    GameOver gameOverFrame;

    public Board(Tetris parent) { // board with tetris parent class

       setFocusable(true); // allows keyboard input because its focused
       currentPiece = new Shape();

       statusbar =  parent.getStatusBar();  // gets status bar 

       // adds main menu
       menu = new MainMenu(this); 
       add(menu);

       timer = new Timer(400, this); // speed of falling pieces (400 ms)
       board = new Tetrominoes[BoardWidth * BoardHeight];  // adds pieces 
       addKeyListener(new TAdapter()); 
       clearBoard();
    }

    public void startGame() { // start game
       remove(menu); // removes main menu

       start(); // starts  the game
    }

    public void actionPerformed(ActionEvent e) { 
        if (isFallingFinished) { // checks to see if falling has finished and whether or not to give out a new piece
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown(); // if it hasnt finished, the piece moves one line down
        }

    }


    // sets up the dimensions of the pieces with respect to board size
    int squareWidth() { 
        return (int) getSize().getWidth() / BoardWidth; 
    } 
    int squareHeight() { 
        return (int) getSize().getHeight() / BoardHeight; 
    }
    Tetrominoes shapeAt(int x, int y) { 
        return board[(y * BoardWidth) + x]; 
    
   }


    public void start()
    {
        if (isPaused) // if the game is paused
            return; 

        
        isStarted = true; // game starts
        isFallingFinished = false; 
        numLinesCleared = 0;
        clearBoard();

        newPiece();
        timer.start();
    }

    private void pause()
    {
        if (!isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
            statusbar.setText("paused");
        } else {
            timer.start();
            statusbar.setText(String.valueOf(numLinesCleared));
        }
        repaint();
    }

    public void reStart() {
        remove(gameOverFrame);
        statusbar.setText("0");
        start();
    }

    public void paint(Graphics g)
    { 
        super.paint(g);

        if (isStarted == false)
            return;
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();


        for (int i = 0; i < BoardHeight; ++i) {
            for (int j = 0; j < BoardWidth; ++j) {
                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
                if (shape != Tetrominoes.NoShape)
                    drawSquare(g, 0 + j * squareWidth(),
                               boardTop + i * squareHeight(), shape);
            }
        }

        if (currentPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = currentX + currentPiece.x(i);
                int y = currentY - currentPiece.y(i);
                drawSquare(g, 0 + x * squareWidth(),
                           boardTop + (BoardHeight - y - 1) * squareHeight(),
                           currentPiece.getShape());
            }
        }
    }

    private void dropDown()
    {
        int newY = currentY;
        while (newY > 0) {
            if (!tryMove(currentPiece, currentX, newY - 1))
                break;
            --newY;
        }
        pieceDropped();
    }

    private void oneLineDown()
    {
        if (!tryMove(currentPiece, currentX, currentY - 1))
            pieceDropped();
    }


    private void clearBoard()
    {
        for (int i = 0; i < BoardHeight * BoardWidth; ++i)
            board[i] = Tetrominoes.NoShape;
    }

    private void pieceDropped()
    {
        for (int i = 0; i < 4; ++i) {
            int x = currentX + currentPiece.x(i);
            int y = currentY - currentPiece.y(i);
            board[(y * BoardWidth) + x] = currentPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished)
            newPiece();
    }

    private void newPiece()
    {
        currentPiece.setRandomShape();
        currentX = BoardWidth / 2 + 1;
        currentY = BoardHeight - 1 + currentPiece.minY();

        if (!tryMove(currentPiece, currentX, currentY)) {
            currentPiece.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            statusbar.setText("game over");

            gameOverFrame = new GameOver(this);
            add(gameOverFrame);

        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY)
    {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NoShape)
                return false;
        }

        currentPiece = newPiece;
        currentX = newX;
        currentY = newY;
        repaint();
        return true;
    }

    private void removeFullLines()
    {
        int numFullLines = 0;

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j)
                         board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
                }
            }
        }

        if (numFullLines > 0) {
            numLinesCleared += numFullLines;
            statusbar.setText(String.valueOf(numLinesCleared));
            isFallingFinished = true;
            currentPiece.setShape(Tetrominoes.NoShape);
            repaint();
        }
     }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) // draws the square
    {
        Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), 
            new Color(102, 204, 102), new Color(102, 102, 204), 
            new Color(204, 204, 102), new Color(204, 102, 204), 
            new Color(102, 204, 204), new Color(218, 170, 0)
        };


        Color color = colors[shape.ordinal()];

        
        // sets the colours of the pieces
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                         x + squareWidth() - 1, y + 1);

    }

    class TAdapter extends KeyAdapter { // adds the ability to use keyboard
         public void keyPressed(KeyEvent e) {

             if (!isStarted || currentPiece.getShape() == Tetrominoes.NoShape) {   
                 return;
             }

             int keycode = e.getKeyCode();

             if (e.getKeyChar() == KeyEvent.VK_ESCAPE) { // if the escape key is pressed then the game pauses
                 pause();
                 return;
             }

             if (isPaused)
                 return;

             // all of the possible controls
             switch (keycode) {
             case KeyEvent.VK_LEFT:
                 tryMove(currentPiece, currentX - 1, currentY);
                 break;
             case KeyEvent.VK_RIGHT:
                 tryMove(currentPiece, currentX + 1, currentY);
                 break;
             case KeyEvent.VK_DOWN:
                 tryMove(currentPiece.rotateRight(), currentX, currentY);
                 break;
             case KeyEvent.VK_UP:
                 tryMove(currentPiece.rotateLeft(), currentX, currentY);
                 break;
             case KeyEvent.VK_SPACE:
                 dropDown();
                 break;
             case 'd':
                 oneLineDown();
                 break;
             case 'D':
                 oneLineDown();
                 break;
             }

         }
     }
}